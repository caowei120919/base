package com.datacvg.dimp.activity;

import android.os.Bundle;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.IndexTreeBean;
import com.datacvg.dimp.bean.IndexTreeListBean;
import com.datacvg.dimp.presenter.TestRealPresenter;
import com.datacvg.dimp.view.TestRealView;
import com.datacvg.dimp.widget.IndexTreeViewFlower;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    public void getDimensionSuccess(DimensionListBean data) {

    }
}
