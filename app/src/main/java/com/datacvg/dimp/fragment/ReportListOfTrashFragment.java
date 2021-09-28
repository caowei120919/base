package com.datacvg.dimp.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.adapter.ReportListOfTrashAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.bean.ReportTrashBean;
import com.datacvg.dimp.bean.ReportTrashListBean;
import com.datacvg.dimp.presenter.ReportListOfTrashPresenter;
import com.datacvg.dimp.view.ReportListOfTrashView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description : 回收站
 */
public class ReportListOfTrashFragment extends BaseFragment<ReportListOfTrashView, ReportListOfTrashPresenter>
        implements ReportListOfTrashView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_reportListOfTrash)
    SwipeRefreshLayout swipeReportListOfTrash ;
    @BindView(R.id.recycler_reportListOfTrash)
    RecyclerView recyclerReportListOfTrash ;

    private ReportListOfTrashAdapter adapter ;
    private List<ReportTrashBean> reportBeans = new ArrayList<>() ;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_trash_list;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        swipeReportListOfTrash.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new ReportListOfTrashAdapter(mContext, reportBeans);
        recyclerReportListOfTrash.setLayoutManager(linearLayoutManager);
        recyclerReportListOfTrash.setAdapter(adapter);
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
        reportBeans.clear();
        queryReportOnTrash();
    }

    /**
     * 报告查询成功
     * @param data
     */
    @Override
    public void queryReportSuccess(ReportTrashListBean data) {
        if(swipeReportListOfTrash.isRefreshing()){
            swipeReportListOfTrash.setRefreshing(false);
        }
        reportBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
