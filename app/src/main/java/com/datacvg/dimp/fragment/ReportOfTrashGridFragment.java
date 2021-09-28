package com.datacvg.dimp.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportGridOfMineAdapter;
import com.datacvg.dimp.adapter.ReportGridOfTrashAdapter;
import com.datacvg.dimp.adapter.ReportListOfTrashAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.ReportTrashBean;
import com.datacvg.dimp.bean.ReportTrashListBean;
import com.datacvg.dimp.presenter.ReportOfTrashPresenter;
import com.datacvg.dimp.view.ReportOfTrashView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public class ReportOfTrashGridFragment extends BaseFragment<ReportOfTrashView, ReportOfTrashPresenter>
        implements ReportOfTrashView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_reportGridOfTrash)
    SwipeRefreshLayout swipeReportGridOfTrash ;
    @BindView(R.id.recycler_reportGridOfTrash)
    RecyclerView recyclerReportGridOfTrash ;

    private List<ReportTrashBean>reportTrashBeans = new ArrayList<>() ;
    private ReportGridOfTrashAdapter adapter ;

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
        swipeReportGridOfTrash.setOnRefreshListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        adapter = new ReportGridOfTrashAdapter(mContext,reportTrashBeans);
        recyclerReportGridOfTrash.setLayoutManager(gridLayoutManager);
        recyclerReportGridOfTrash.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        queryReportOnTrash();
    }

    /**
     * 查询回收站所有画布资源
     */
    private void queryReportOnTrash() {
        getPresenter().queryReport(Constants.REPORT_MINE,System.currentTimeMillis() + "");
        getPresenter().queryReport(Constants.REPORT_SHARE,System.currentTimeMillis() + "");
        getPresenter().queryReport(Constants.REPORT_TEMPLATE,System.currentTimeMillis() + "");
    }

    @Override
    public void onRefresh() {
        reportTrashBeans.clear();
        queryReportOnTrash();
    }

    @Override
    public void queryReportSuccess(ReportTrashListBean data) {
        if(swipeReportGridOfTrash.isRefreshing()){
            swipeReportGridOfTrash.setRefreshing(false);
        }
        reportTrashBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
