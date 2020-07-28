package com.datacvg.sempmobile;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.datacvg.sempmobile.baseandroid.BaseApplication;
import com.datacvg.sempmobile.baseandroid.config.Api;
import com.datacvg.sempmobile.baseandroid.dragger.component.DaggerMyAppComponent;
import com.datacvg.sempmobile.baseandroid.dragger.component.MyAppComponent;
import com.datacvg.sempmobile.baseandroid.dragger.module.MyAppModule;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public class MyApplication extends BaseApplication {
    private static MyAppComponent mMyAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setDebugMode(BuildConfig.DEBUG);
        initMyAppInject(this);
        Api.setEnviroment(BuildConfig.APP_ENV);
//        GaoDeMap.getInstance().init(this);
    }


    public void initMyAppInject(Context context) {
        mMyAppComponent = DaggerMyAppComponent.builder().
                appComponent(getAppComponent()).myAppModule(new MyAppModule()).build();
    }

    public static MyAppComponent getMyAppComponent() {
        return mMyAppComponent;
    }

    public static String getAppVersionName(Application application) {
        if (application != null) {
            PackageManager pm = application.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(application.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionName;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
