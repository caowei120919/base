package com.datacvg.dimp.fragment;

import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.ActionListPresenter;
import com.datacvg.dimp.view.ActionListView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ActionListFragment extends BaseFragment<ActionListView, ActionListPresenter>
        implements ActionListView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_action_list;
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
