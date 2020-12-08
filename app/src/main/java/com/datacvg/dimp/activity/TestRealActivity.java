package com.datacvg.dimp.activity;

import android.os.Bundle;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.presenter.TestRealPresenter;
import com.datacvg.dimp.view.TestRealView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-28
 * @Description : 测试专用
 */
public class TestRealActivity extends BaseActivity<TestRealView, TestRealPresenter>
        implements TestRealView {
    private String mTimeValue;

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
        mTimeValue = PreferencesHelper.get(Constants.USER_DEFAULT_TIME,"") ;
        getPresenter().getDimension(TimeUtils.getNewStrDateForStr(mTimeValue,TimeUtils.FORMAT_YM));
    }

    @Override
    public void getDimensionSuccess(DimensionListBean data) {

    }
}
