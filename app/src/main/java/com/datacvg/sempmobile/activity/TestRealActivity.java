package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.presenter.TestRealPresenter;
import com.datacvg.sempmobile.view.TestRealView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-28
 * @Description : 测试专用
 */
public class TestRealActivity extends BaseActivity<TestRealView, TestRealPresenter>
        implements TestRealView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
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
