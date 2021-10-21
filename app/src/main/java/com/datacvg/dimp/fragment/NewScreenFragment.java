package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.NewScreenPresenter;
import com.datacvg.dimp.view.NewScreenView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description : 新屏
 */
public class NewScreenFragment extends BaseFragment<NewScreenView, NewScreenPresenter>
        implements NewScreenView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_screen;
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
