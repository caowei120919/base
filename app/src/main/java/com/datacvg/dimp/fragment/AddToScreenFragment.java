package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.AddToScreenPresenter;
import com.datacvg.dimp.view.AddToScreenView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description : 大屏
 */
public class AddToScreenFragment extends BaseFragment<AddToScreenView, AddToScreenPresenter> implements AddToScreenView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_to_screen;
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
