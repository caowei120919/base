package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportListOfSharedPresenter;
import com.datacvg.dimp.view.ReportListOfSharedView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description : 共享管理画布列表
 */
public class ReportListOfSharedFragment extends BaseFragment<ReportListOfSharedView, ReportListOfSharedPresenter>
        implements ReportListOfSharedView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_shared_list;
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
