package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.presenter.ReportListPresenter;
import com.datacvg.dimp.view.ReportListView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ReportListFragment extends BaseFragment<ReportListView, ReportListPresenter>
        implements ReportListView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_list;
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
