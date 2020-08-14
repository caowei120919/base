package com.datacvg.sempmobile.baseandroid.retrofit.helper;

import android.content.Context;

import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.ApplicationContext;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.OkhttpCache;
import com.datacvg.sempmobile.baseandroid.dragger.scope.MyAppScope;
import com.datacvg.sempmobile.baseandroid.okhttp.OkhttpCacheUtils;
import com.datacvg.sempmobile.baseandroid.okhttp.RequestMethod;
import com.datacvg.sempmobile.baseandroid.okhttp.SSLSocketClient;
import com.datacvg.sempmobile.baseandroid.oklog.OkLogInterceptor;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 网络接口配置相关
 */
@MyAppScope
public class MyOkhttpHelper {
    private static ClearableCookieJar sCookieJar;
    private OkHttpClient mHttpClient = null;

    @Inject
    public MyOkhttpHelper(@ApplicationContext Context context, @OkhttpCache File cacheFile) {

        sCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(AndroidUtils
                .getContext()));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(OkhttpCacheUtils.createCache(context, cacheFile.getAbsolutePath()))
                .cookieJar(sCookieJar)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
//                .addInterceptor(userAgentInterceptor)
                .addInterceptor(headerInterceptor);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        OkLogInterceptor okLogInterceptor = new OkLogInterceptor();
        builder.addInterceptor(okLogInterceptor);


        mHttpClient = RetrofitUrlManager.getInstance().with(builder).build();
    }


    private static Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder newRequestBuilder = originalRequest.newBuilder();
            newRequestBuilder.addHeader(Constants.authorization, Constants.token);
            StringBuilder postString = new StringBuilder();
            if (RequestMethod.supportBody(originalRequest.method())) {
                if (originalRequest.body() instanceof FormBody) {
                    FormBody formBody = (FormBody) originalRequest.body();
                    for (int i = 0; i < formBody.size(); i++) {
                        postString.append(postString.length() > 0 ? "&" : "")
                                .append(formBody.encodedName(i))
                                .append("=")
                                .append(formBody.encodedValue(i) == null ? "" : formBody.encodedValue(i));
                    }
                }
            }


            Response response = chain.proceed(newRequestBuilder.build());
            MediaType mediaType = response.body().contentType();
            try {
                String responseContent = response.body().string();
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, responseContent))
                        .build();
            } catch (Exception e) {
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, ""))
                        .build();
            }
        }
    };

    public static List<Cookie> getHttpCookieList() {
        return sCookieJar.loadForRequest(HttpUrl.parse(Constants.BASE_URL));
    }

    public static String getHttpCookieString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Cookie> cookieList = getHttpCookieList();
        for (Cookie cookie : cookieList) {
            stringBuilder.append(cookie.toString()).append(";");
        }
        if (stringBuilder.length() > 0) {
            int last = stringBuilder.lastIndexOf(";");
            if (stringBuilder.length() - 1 == last) {
                stringBuilder.deleteCharAt(last);
            }
        }

        return stringBuilder.toString();
    }

    public static void clearCookie() {
        sCookieJar.clear();
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

}
