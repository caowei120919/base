package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ActionPopAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ActionForIndexBean;
import com.datacvg.dimp.presenter.ActionListPresenter;
import com.datacvg.dimp.view.ActionListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ActionListFragment extends BaseFragment<ActionListView, ActionListPresenter>
        implements ActionListView {
    @BindView(R.id.tv_emptyView)
    TextView tvEmptyView ;
    @BindView(R.id.recycler_action)
    RecyclerView recyclerAction ;

    private String indexId ;
    private List<ActionForIndexBean.ActionIndexBean> actionIndexBeans = new ArrayList<>();
    private ActionPopAdapter adapter ;

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
        indexId = getArguments().getString(Constants.EXTRA_DATA_FOR_BEAN);
        PLog.e("indexId ===== >" + indexId);
        initAdapter();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new ActionPopAdapter(mContext,actionIndexBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerAction.setLayoutManager(manager);
        recyclerAction.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getActionForIndexId(indexId);
    }

    public static ActionListFragment newInstance(String indexId) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_DATA_FOR_BEAN,indexId);
        ActionListFragment fragment = new ActionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getActionForIndexSuccess(ActionForIndexBean data) {
        actionIndexBeans.clear();
        actionIndexBeans.addAll(data.getSearchActionPlan());
        if(actionIndexBeans.isEmpty()){
            tvEmptyView.setVisibility(View.VISIBLE);
        }else{
            tvEmptyView.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
}
