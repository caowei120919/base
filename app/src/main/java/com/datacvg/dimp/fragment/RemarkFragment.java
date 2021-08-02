package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.presenter.RemarkPresenter;
import com.datacvg.dimp.view.RemarkView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class RemarkFragment extends BaseFragment<RemarkView, RemarkPresenter> implements RemarkView {

    private String indexId ;

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
        indexId = getArguments().getString(Constants.EXTRA_DATA_FOR_BEAN);
        PLog.e("indexId ===== >" + indexId);
    }

    @Override
    protected void setupData() {

    }

    public static RemarkFragment newInstance(String indexId) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_DATA_FOR_BEAN,indexId);
        RemarkFragment fragment = new RemarkFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
