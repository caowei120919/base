package com.datacvg.sempmobile.activity;

import android.os.Bundle;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.presenter.MyIndexPresenter;
import com.datacvg.sempmobile.view.MyIndexView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-30
 * @Description : 我的指标
 */
public class MyIndexActivity extends BaseActivity<MyIndexView, MyIndexPresenter> implements MyIndexView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
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
