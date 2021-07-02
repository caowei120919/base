package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.BudgetPresenter;
import com.datacvg.dimp.view.BudgetView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description : 指标绩效
 */
public class BudgetFragment extends BaseFragment<BudgetView, BudgetPresenter> implements BudgetView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {

    }

    @Override
    protected void setupData() {

    }
}
