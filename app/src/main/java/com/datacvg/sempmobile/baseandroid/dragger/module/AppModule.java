package com.datacvg.sempmobile.baseandroid.dragger.module;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.datacvg.sempmobile.baseandroid.dragger.qualifier.ApplicationContext;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.GlideCache;
import com.datacvg.sempmobile.baseandroid.dragger.qualifier.OkhttpCache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * FileName: AppModule
 * Author: 曹伟
 * Date: 2019/9/16 16:04
 * Description:
 */

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    File provideCacheFile(@ApplicationContext Context application) {
        return getCacheFile(application);
    }

    @Singleton
    @Provides
    @OkhttpCache
    File provideOkhttpCache(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "okhttp-cache");
        return makeDirs(cacheDirectory);
    }

    @Singleton
    @Provides
    @GlideCache
    File provideGlideCache(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "glide-cache");
        return makeDirs(cacheDirectory);
    }

    public static File getCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = null;
            file = context.getExternalCacheDir();
            if (file == null) {
                String packageName = context.getPackageName();
                file = new File(Environment.getExternalStorageDirectory()
                        .getPath() + File.separator + packageName);
                makeDirs(file);
            }
            return file;
        } else {
            return context.getCacheDir();
        }
    }

    public static File makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
