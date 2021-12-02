package com.datacvg.dimp.fragment;

import android.view.View;

import androidx.annotation.NonNull;
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
import com.datacvg.dimp.event.ReportRefreshEvent;
import com.datacvg.dimp.event.ReportTrashCheckAllEvent;
import com.datacvg.dimp.event.ReportTrashEvent;
import com.datacvg.dimp.event.ReportTrashInCheckAllEvent;
import com.datacvg.dimp.event.ReportTrashNotAllCheckEvent;
import com.datacvg.dimp.presenter.ReportListOfTrashPresenter;
import com.datacvg.dimp.view.ReportListOfTrashView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description : 回收站
 */
public class ReportListOfTrashFragment extends BaseFragment<ReportListOfTrashView, ReportListOfTrashPresenter>
        implements ReportListOfTrashView, OnRefreshListener, ReportListOfTrashAdapter.OnReportTrashClickListener {
    @BindView(R.id.swipe_reportListOfTrash)
    SmartRefreshLayout swipeReportListOfTrash ;
    @BindView(R.id.recycler_reportListOfTrash)
    RecyclerView recyclerReportListOfTrash ;

    private boolean isEdit = false ;
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
        swipeReportListOfTrash.setEnableAutoLoadMore(false);
        swipeReportListOfTrash.setOnRefreshListener(this);
        swipeReportListOfTrash.setEnableRefresh(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new ReportListOfTrashAdapter(mContext,this, reportBeans,isEdit);
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

    /**
     * 报告查询成功
     * @param data
     */
    @Override
    public void queryReportSuccess(ReportTrashListBean data) {
        if(swipeReportListOfTrash.isRefreshing()){
            swipeReportListOfTrash.finishRefresh();
        }
        reportBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        reportBeans.clear();
        queryReportOnTrash();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReportTrashEvent event){
        adapter.setEdit(!event.getEdit());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReportRefreshEvent event){
        reportBeans.clear();
        getPresenter().queryReport(Constants.REPORT_MINE,System.currentTimeMillis() + "");
        getPresenter().queryReport(Constants.REPORT_SHARE,System.currentTimeMillis() + "");
        getPresenter().queryReport(Constants.REPORT_TEMPLATE,System.currentTimeMillis() + "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReportTrashCheckAllEvent event){
        for (ReportTrashBean reportTrashBean : reportBeans){
            reportTrashBean.setChecked(event.isCheckAll());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void checkReport(ReportTrashBean bean) {
        for (ReportTrashBean reportTrashBean : reportBeans){
            if(!reportTrashBean.getChecked()){
                EventBus.getDefault().post(new ReportTrashNotAllCheckEvent());
                return;
            }
        }
        EventBus.getDefault().post(new ReportTrashInCheckAllEvent());
    }
}
