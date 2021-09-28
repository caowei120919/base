package com.datacvg.dimp.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.ReportListOfMinePresenter;
import com.datacvg.dimp.view.ReportListOfMineView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description : 我的管理画布列表
 */
public class ReportListOfMineFragment extends BaseFragment<ReportListOfMineView, ReportListOfMinePresenter>
        implements ReportListOfMineView, SwipeRefreshLayout.OnRefreshListener, ReportListAdapter.OnReportListener {
    @BindView(R.id.swipe_reportListOfMine)
    SwipeRefreshLayout swipeReportListOfMine ;
    @BindView(R.id.recycler_reportListOfMine)
    RecyclerView recyclerReportListOfMine ;

    private List<ReportBean> reportBeans = new ArrayList<>();
    private ReportListAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_mine_list;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        swipeReportListOfMine.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new ReportListAdapter(mContext,Constants.REPORT_MINE,reportBeans,this);
        recyclerReportListOfMine.setLayoutManager(linearLayoutManager);
        recyclerReportListOfMine.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_MINE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onRefresh() {
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_MINE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getReportOfMineSuccess(ReportListBean data) {
        if(swipeReportListOfMine.isRefreshing()){
            swipeReportListOfMine.setRefreshing(false);
        }
        this.reportBeans.clear();
        this.reportBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteComplete(String model_id) {
        for (ReportBean reportBean : reportBeans){
            if(reportBean.getModel_id().equals(model_id)){
                reportBeans.remove(reportBean);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 报告被删除
     * @param reportBean
     */
    @Override
    public void onReportDelete(ReportBean reportBean) {
        getPresenter().deleteReport(reportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
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
}
