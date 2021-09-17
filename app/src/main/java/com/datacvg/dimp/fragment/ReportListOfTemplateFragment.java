package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportListOfTemplatePresenter;
import com.datacvg.dimp.view.ReportListOfTemplateView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfTemplateFragment extends BaseFragment<ReportListOfTemplateView, ReportListOfTemplatePresenter>
        implements ReportListOfTemplateView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_template_list;
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
