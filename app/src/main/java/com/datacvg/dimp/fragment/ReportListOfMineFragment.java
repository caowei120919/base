package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportListOfMinePresenter;
import com.datacvg.dimp.view.ReportListOfMineView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description : 我的管理画布列表
 */
public class ReportListOfMineFragment extends BaseFragment<ReportListOfMineView, ReportListOfMinePresenter>
        implements ReportListOfMineView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_mine_list;
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
