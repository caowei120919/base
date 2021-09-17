package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ReportOfTrashPresenter;
import com.datacvg.dimp.view.ReportOfTrashView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public class ReportOfTrashGridFragment extends BaseFragment<ReportOfTrashView, ReportOfTrashPresenter>
        implements ReportOfTrashView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_trash;
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
