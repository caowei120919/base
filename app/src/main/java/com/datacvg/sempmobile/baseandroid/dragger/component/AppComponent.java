package com.datacvg.sempmobile.baseandroid.dragger.component;

import android.content.Context;

import com.datacvg.sempmobile.baseandroid.dragger.module.AppModule;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.ApplicationContext;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.GlideCache;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.OkhttpCache;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.OkhttpHelper;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.RetrofitHelper;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;

/**
 * FileName: AppComponent
 * Author: 曹伟
 * Date: 2019/9/16 16:03
 * Description:
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    @ApplicationContext
    Context getContext();

    File getCacheDir();

    @OkhttpCache
    File getOkhttpCacheDir();

    @GlideCache
    File getGlideCacheDir();

    OkhttpHelper okhttpHelper();

    RetrofitHelper RetrofitHelper();

    PreferencesHelper preferencesHelper();

}