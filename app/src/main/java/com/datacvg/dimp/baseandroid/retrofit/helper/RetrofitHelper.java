package com.datacvg.dimp.baseandroid.retrofit.helper;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.NobodyConverterFactory;
import com.google.gson.GsonBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * FileName: RetrofitHelper
 * Author: 曹伟
 * Date: 2019/9/16 14:46
 * Description:
 */

@Singleton
public class RetrofitHelper {

    private Retrofit mRetrofit = null;

    @Inject
    public RetrofitHelper() {
        mRetrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()))
                .addConverterFactory(NobodyConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * .addConverterFactory(ScalarsConverterFactory.create())
     .addConverterFactory(GsonConverterFactory.create(onCreateGson()))
     .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
     * @return
     */

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
