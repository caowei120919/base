package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportOfSharedPresenter;
import com.datacvg.dimp.view.ReportOfSharedView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :管理画布共享
 */
public class ReportOfSharedGridFragment extends BaseFragment<ReportOfSharedView, ReportOfSharedPresenter>
        implements ReportOfSharedView{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_shared;
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
