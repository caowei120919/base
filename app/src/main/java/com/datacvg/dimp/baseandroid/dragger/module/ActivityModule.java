package com.datacvg.dimp.baseandroid.dragger.module;

import android.app.Activity;


import com.datacvg.dimp.baseandroid.dragger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * FileName: ActivityModule
 * Author: 曹伟
 * Date: 2019/9/16 14:43
 * Description:
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
