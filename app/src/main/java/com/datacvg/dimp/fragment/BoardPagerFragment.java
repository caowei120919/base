package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.presenter.BoardPagerPresenter;
import com.datacvg.dimp.view.BoardPagerView;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class BoardPagerFragment extends BaseFragment<BoardPagerView, BoardPagerPresenter>{
    @BindView(R.id.tv_pageName)
    TextView tvPageName ;

    private PageItemBean itemBean ;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_board_pager;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        itemBean = (PageItemBean) getArguments().getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
        tvPageName.setText(itemBean.getPad_name());
    }

    public static BoardPagerFragment newInstance(PageItemBean bean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,bean);
        BoardPagerFragment fragment = new BoardPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
