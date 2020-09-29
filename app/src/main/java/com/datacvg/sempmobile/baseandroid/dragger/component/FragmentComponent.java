package com.datacvg.sempmobile.baseandroid.dragger.component;

import android.app.Activity;

import com.datacvg.sempmobile.dragger.module.FragmentModule;
import com.datacvg.sempmobile.dragger.scope.FragmentScope;
import com.datacvg.sempmobile.fragment.ActionFragment;
import com.datacvg.sempmobile.fragment.DigitalFragment;
import com.datacvg.sempmobile.fragment.PersonalFragment;
import com.datacvg.sempmobile.fragment.ReportFragment;
import com.datacvg.sempmobile.fragment.ScreenFragment;
import com.datacvg.sempmobile.fragment.TableFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = MyAppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(PersonalFragment personalFragment);

    void inject(ScreenFragment screenFragment);

    void inject(DigitalFragment digitalFragment);

    void inject(ReportFragment reportFragment);

    void inject(TableFragment tableFragment);

    void inject(ActionFragment actionFragment);
}
