package com.datacvg.sempmobile.activity;

import android.os.Bundle;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.presenter.MainPresenter;
import com.datacvg.sempmobile.view.MainView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :主页，
 */
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
