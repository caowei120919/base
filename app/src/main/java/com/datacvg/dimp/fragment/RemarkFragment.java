package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.RemarkPresenter;
import com.datacvg.dimp.view.RemarkView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class RemarkFragment extends BaseFragment<RemarkView, RemarkPresenter> implements RemarkView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_remark;
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
