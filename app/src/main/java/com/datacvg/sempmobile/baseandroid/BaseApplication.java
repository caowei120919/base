package com.datacvg.sempmobile.baseandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.datacvg.sempmobile.baseandroid.download.DownloadConfiguration;
import com.datacvg.sempmobile.baseandroid.download.DownloadManager;
import com.datacvg.sempmobile.baseandroid.dragger.component.AppComponent;
import com.datacvg.sempmobile.baseandroid.dragger.component.DaggerAppComponent;
import com.datacvg.sempmobile.baseandroid.dragger.module.AppModule;
import com.datacvg.sempmobile.baseandroid.manager.BaseAppManager;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.facebook.stetho.Stetho;
import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 基类application，处理一些事件处理
 */
public class BaseApplication extends Application {
    private static AppComponent mAppComponent;
    private static BaseAppManager mAppManager;
    private static RefWatcher mRefWatcher;

    public static boolean DEBUGMODE = false;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidUtils.init(this);

        initAppInject(this);

        initDownloader(this);

        Hawk.init(this).build();

        registerFlutter();

    }

    /**
     * 注册flutter
     */
    private void registerFlutter() {

    }

    public static void setDebugMode(boolean debugmode) {
        BaseApplication.DEBUGMODE = debugmode;

        if (BaseApplication.DEBUGMODE) {
            mRefWatcher = LeakCanary.install((Application) AndroidUtils.getContext());

            Stetho.initializeWithDefaults(AndroidUtils.getContext());

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());

        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        return mRefWatcher;
    }

    private static void initDownloader(Context context) {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(6);
        DownloadManager.getInstance().init(context, configuration);
    }

    public static BaseAppManager getAppManager() {
        if (mAppManager == null) {
            synchronized (BaseApplication.class) {
                if (mAppManager == null) {
                    mAppManager = BaseAppManager.getManager();
                }
            }
        }
        return mAppManager;
    }

    public void initAppInject(Context context) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((Application) context))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    public static void exitApp() {
        mAppManager.exitApp();
    }

    public static void restartApp(@NonNull Activity activity) {
        Intent intent = activity.getPackageManager()
                .getLaunchIntentForPackage(activity.getPackageName());
        Class<? extends Activity> resolvedActivityClass;
        if (intent != null && intent.getComponent() != null) {
            try {
                resolvedActivityClass = (Class<? extends Activity>) Class.forName(intent.getComponent()
                        .getClassName());
                intent.setClass(activity, resolvedActivityClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                if (intent.getComponent() != null) {
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                }
                activity.finish();
                activity.startActivity(intent);
                exitApp();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
