package com.datacvg.sempmobile.baseandroid.dragger.component;

import android.app.Activity;


import com.datacvg.sempmobile.activity.LoginActivity;
import com.datacvg.sempmobile.activity.MainActivity;
import com.datacvg.sempmobile.activity.QRCodeActivity;
import com.datacvg.sempmobile.activity.ScanActivity;
import com.datacvg.sempmobile.activity.SettingVpnActivity;
import com.datacvg.sempmobile.activity.SplashActivity;
import com.datacvg.sempmobile.baseandroid.dragger.module.ActivityModule;
import com.datacvg.sempmobile.baseandroid.dragger.scope.ActivityScope;

import dagger.Component;

/**
 * @author 曹伟
 */
@ActivityScope
@Component(dependencies = MyAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(SettingVpnActivity settingVpnActivity);

    void inject(QRCodeActivity qrCodeActivity);

    void inject(ScanActivity scanActivity);
}
