package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportOfTemplatePresenter;
import com.datacvg.dimp.view.ReportOfTemplateView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :管理画布模板
 */
public class ReportOfTemplateGridFragment extends BaseFragment<ReportOfTemplateView, ReportOfTemplatePresenter>
        implements ReportOfTemplateView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_template;
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
