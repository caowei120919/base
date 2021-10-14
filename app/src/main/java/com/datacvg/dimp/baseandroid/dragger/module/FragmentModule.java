package com.datacvg.dimp.baseandroid.dragger.module;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import com.datacvg.dimp.baseandroid.dragger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * FileName: FragmentModule
 * Author: 曹伟
 * Date: 2019/9/16 18:51
 * Description:
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
