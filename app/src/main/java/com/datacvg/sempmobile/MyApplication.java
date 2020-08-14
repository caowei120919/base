package com.datacvg.sempmobile;

import android.content.Context;
import com.datacvg.sempmobile.baseandroid.BaseApplication;
import com.datacvg.sempmobile.baseandroid.config.Api;
import com.datacvg.sempmobile.baseandroid.dragger.component.DaggerMyAppComponent;
import com.datacvg.sempmobile.baseandroid.dragger.component.MyAppComponent;
import com.datacvg.sempmobile.baseandroid.dragger.module.MyAppModule;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;

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
        initMyAppInject();
        Api.setEnviroment(BuildConfig.APP_ENV);
        AndroidUtils.init(this);
        initFlutterBoost();
    }

    /**
     * 初始化flutter启动引擎追踪
     */
    private void initFlutterBoost() {

    }


    public void initMyAppInject() {
        mMyAppComponent = DaggerMyAppComponent.builder().
                appComponent(getAppComponent()).myAppModule(new MyAppModule()).build();
    }

    public static MyAppComponent getMyAppComponent() {
        return mMyAppComponent;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
