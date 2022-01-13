package com.datacvg.dimp.activity;

import android.os.Bundle;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.presenter.InComeDetailPresenter;
import com.datacvg.dimp.view.InComeDetailView;
import com.datacvg.dimp.widget.VerticalProgressBar;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-09
 * @Description :
 */
public class InComeDetailActivity extends BaseActivity<InComeDetailView, InComeDetailPresenter>
        implements InComeDetailView {
    @BindView(R.id.verticalPro)
    VerticalProgressBar verticalPro ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_income;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        verticalPro.setProgress(60);
    }
}
