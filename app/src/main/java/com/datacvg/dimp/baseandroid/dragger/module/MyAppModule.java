package com.datacvg.dimp.baseandroid.dragger.module;


import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.FisApi;
import com.datacvg.dimp.baseandroid.config.LoginApi;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.config.UploadApi;
import com.datacvg.dimp.baseandroid.dragger.qualifier.FisUrl;
import com.datacvg.dimp.baseandroid.dragger.qualifier.LoginUrl;
import com.datacvg.dimp.baseandroid.dragger.qualifier.MobileUrl;
import com.datacvg.dimp.baseandroid.dragger.scope.MyAppScope;
import com.datacvg.dimp.baseandroid.retrofit.helper.RetrofitHelper;
import com.datacvg.dimp.baseandroid.utils.StringUtils;
import com.datacvg.dimp.dragger.qualifier.UploadUrl;
import com.datacvg.dimp.baseandroid.retrofit.helper.MyOkhttpHelper;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class MyAppModule {

    @MyAppScope
    @Provides
    @LoginUrl
    Retrofit provideLoginUrlRetrofit(MyOkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper) {
        return createLoginApiRetrofit(okhttpHelper, retrofitHelper, Constants.BASE_URL);
    }

    private Retrofit createLoginApiRetrofit(MyOkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper, String url) {
        Retrofit.Builder builder = retrofitHelper.getRetrofit().newBuilder();
        OkHttpClient.Builder client = okhttpHelper.getHttpClient().newBuilder();
        return builder.baseUrl(url).client(client.build()).build();
    }

    @MyAppScope
    @Provides
    @FisUrl
    Retrofit provideFisUrlRetrofit(MyOkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper) {
        return createFisApiRetrofit(okhttpHelper, retrofitHelper
                , StringUtils.isEmpty(Constants.BASE_FIS_URL) ? Constants.BASE_URL : Constants.BASE_FIS_URL);
    }

    private Retrofit createFisApiRetrofit(MyOkhttpHelper okhttpHelper
            , RetrofitHelper retrofitHelper, String baseUrl) {
        Retrofit.Builder builder = retrofitHelper.getRetrofit().newBuilder();
        OkHttpClient.Builder client = okhttpHelper.getHttpClient().newBuilder();
        return builder.baseUrl(baseUrl).client(client.build()).build();
    }

    @MyAppScope
    @Provides
    @MobileUrl
    Retrofit provideMobileUrlRetrofit(MyOkhttpHelper okhttpHelper , RetrofitHelper retrofitHelper){
        return createMobileApiRetrofit(okhttpHelper,retrofitHelper
                , StringUtils.isEmpty(Constants.BASE_MOBILE_URL) ? Constants.BASE_URL : Constants.BASE_MOBILE_URL);
    }

    private Retrofit createMobileApiRetrofit(MyOkhttpHelper okhttpHelper
            , RetrofitHelper retrofitHelper, String baseUrl) {
        Retrofit.Builder builder = retrofitHelper.getRetrofit().newBuilder();
        OkHttpClient.Builder client = okhttpHelper.getHttpClient().newBuilder();
        return builder.baseUrl(baseUrl).client(client.build()).build();
    }


    @MyAppScope
    @Provides
    @UploadUrl
    Retrofit provideUploadUrlRetrofit(MyOkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper) {
        return createUploadApiRetrofit(okhttpHelper, retrofitHelper,  Constants.BASE_URL);
    }


    private Retrofit createUploadApiRetrofit(MyOkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper, String upLoadingUrl) {
        Retrofit.Builder builder = retrofitHelper.getRetrofit().newBuilder();
        OkHttpClient.Builder client = okhttpHelper.getHttpClient().newBuilder();
        return builder.baseUrl(upLoadingUrl).client(client.build()).build();
    }

    @MyAppScope
    @Provides
    LoginApi provideLoginApi(@LoginUrl Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }


    @MyAppScope
    @Provides
    UploadApi provideUploadApi(@UploadUrl Retrofit retrofit) {
        return retrofit.create(UploadApi.class);
    }

    @MyAppScope
    @Provides
    MobileApi provideMobileApi(@MobileUrl Retrofit retrofit) {
        return retrofit.create(MobileApi.class);
    }

    @MyAppScope
    @Provides
    FisApi provideFisApi(@FisUrl Retrofit retrofit) {
        return retrofit.create(FisApi.class);
    }
}
