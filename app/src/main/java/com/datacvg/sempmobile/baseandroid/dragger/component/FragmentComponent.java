package com.datacvg.sempmobile.baseandroid.dragger.component;

import android.app.Activity;

import com.datacvg.sempmobile.dragger.module.FragmentModule;
import com.datacvg.sempmobile.dragger.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = MyAppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
}
