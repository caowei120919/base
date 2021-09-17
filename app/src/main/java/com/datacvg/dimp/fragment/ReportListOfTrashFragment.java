package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportListOfTrashPresenter;
import com.datacvg.dimp.view.ReportListOfTrashView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description : 回收站
 */
public class ReportListOfTrashFragment extends BaseFragment<ReportListOfTrashView, ReportListOfTrashPresenter>
        implements ReportListOfTrashView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_trash_list;
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
