package com.datacvg.sempmobile.baseandroid.dragger.component;

import android.app.Activity;


import com.datacvg.sempmobile.activity.LoginActivity;
import com.datacvg.sempmobile.activity.SplashActivity;
import com.datacvg.sempmobile.baseandroid.dragger.module.ActivityModule;
import com.datacvg.sempmobile.baseandroid.dragger.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = MyAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);
}
