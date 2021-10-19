package com.datacvg.dimp.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.ReportListOfTemplatePresenter;
import com.datacvg.dimp.view.ReportListOfTemplateView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfTemplateFragment extends BaseFragment<ReportListOfTemplateView, ReportListOfTemplatePresenter>
        implements ReportListOfTemplateView, ReportListAdapter.OnReportListener, OnRefreshListener {
    @BindView(R.id.swipe_reportListOfTemplate)
    SmartRefreshLayout swipeReportListOfTemplate ;
    @BindView(R.id.recycler_reportListOfTemplate)
    RecyclerView recyclerReportListOfTemplate ;

    private List<ReportBean> reportBeans = new ArrayList<>();
    private ReportListAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_template_list;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        swipeReportListOfTemplate.setEnableAutoLoadMore(false);
        swipeReportListOfTemplate.setOnRefreshListener(this);
        swipeReportListOfTemplate.setEnableRefresh(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new ReportListAdapter(mContext, Constants.REPORT_TEMPLATE,reportBeans,this);
        recyclerReportListOfTemplate.setLayoutManager(linearLayoutManager);
        recyclerReportListOfTemplate.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getReportOfTemplateSuccess(ReportListBean data) {
        if(swipeReportListOfTemplate.isRefreshing()){
            swipeReportListOfTemplate.finishRefresh();
        }
        this.reportBeans.clear();
        this.reportBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }

    /**
     * 报告被删除
     * @param reportBean
     */
    @Override
    public void onReportDelete(ReportBean reportBean) {

    }

    /**
     * 报告被添加到大屏
     * @param reportBean
     */
    @Override
    public void onReportAddToScreen(ReportBean reportBean) {

    }

    /**
     * 报告被下载
     * @param reportBean
     */
    @Override
    public void onReportDownload(ReportBean reportBean) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }
}
