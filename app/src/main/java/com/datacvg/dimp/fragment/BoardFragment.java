package com.datacvg.dimp.fragment;

import android.view.View;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.presenter.BoardPresenter;
import com.datacvg.dimp.view.BoardView;
import com.datacvg.dimp.widget.ControlScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public class BoardFragment extends BaseFragment<BoardView, BoardPresenter> implements BoardView {

    @BindView(R.id.tv_pageName)
    TextView tvPageName ;
    @BindView(R.id.tv_pageTime)
    TextView tvPageTime ;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.vp_board)
    ControlScrollViewPager vpBoard ;

    private List<PageItemBean> pageItemBeans = new ArrayList<>() ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_board;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupData() {
        getPresenter().getBoardPage();
    }

    @Override
    protected void setupView(View rootView) {
        initPageAdapter();
        initMagicIndicator();
    }

    private void initMagicIndicator() {

    }

    private void initPageAdapter() {

    }

    @Override
    public void getPageSuccess(List<PageItemBean> pageItemBeans) {

    }
}
