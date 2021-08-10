package com.datacvg.dimp.baseandroid.dragger.component;

import android.app.Activity;

import com.datacvg.dimp.dragger.module.FragmentModule;
import com.datacvg.dimp.dragger.scope.FragmentScope;
import com.datacvg.dimp.fragment.ActionFragment;
import com.datacvg.dimp.fragment.ActionListFragment;
import com.datacvg.dimp.fragment.BoardFragment;
import com.datacvg.dimp.fragment.BoardPagerFragment;
import com.datacvg.dimp.fragment.BudgetFragment;
import com.datacvg.dimp.fragment.DigitalFragment;
import com.datacvg.dimp.fragment.EmptyBoardFragment;
import com.datacvg.dimp.fragment.PersonalFragment;
import com.datacvg.dimp.fragment.RemarkFragment;
import com.datacvg.dimp.fragment.ReportFragment;
import com.datacvg.dimp.fragment.ReportListFragment;
import com.datacvg.dimp.fragment.ScreenFragment;
import com.datacvg.dimp.fragment.SnapFragment;
import com.datacvg.dimp.fragment.TableFragment;

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

    void inject(BoardPagerFragment boardPagerFragment);

    void inject(BoardFragment boardFragment);

    void inject(BudgetFragment budgetFragment);

    void inject(EmptyBoardFragment emptyBoardFragment);

    void inject(ReportListFragment reportListFragment);

    void inject(ActionListFragment actionListFragment);

    void inject(RemarkFragment remarkFragment);

    void inject(SnapFragment snapFragment);
}
