package com.datacvg.sempmobile.fragment;

import android.os.Bundle;
import android.view.View;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.ActionPresenter;
import com.datacvg.sempmobile.view.ActionView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 行动方案
 */
public class ActionFragment extends BaseFragment<ActionView, ActionPresenter> implements ActionView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_action;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
