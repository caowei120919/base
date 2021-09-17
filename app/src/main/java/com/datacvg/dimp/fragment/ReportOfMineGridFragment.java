package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.ReportDetailActivity;
import com.datacvg.dimp.activity.ReportFolderActivity;
import com.datacvg.dimp.adapter.ReportGridOfMineAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.ReportOfMinePresenter;
import com.datacvg.dimp.view.ReportOfMineView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description : 管理画布我的
 */
public class ReportOfMineGridFragment extends BaseFragment<ReportOfMineView, ReportOfMinePresenter>
        implements ReportOfMineView, SwipeRefreshLayout.OnRefreshListener, ReportGridOfMineAdapter.OnReportClickListener {
    @BindView(R.id.swipe_reportOfMine)
    SwipeRefreshLayout swipeReportOfMine ;
    @BindView(R.id.recycler_reportOfMine)
    RecyclerView recyclerReportOfMine ;

    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private ReportGridOfMineAdapter reportAdapter ;
    private GridLayoutManager gridLayoutManager ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_mine;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        swipeReportOfMine.setOnRefreshListener(this);
        gridLayoutManager = new GridLayoutManager(mContext,2);
        reportAdapter = new ReportGridOfMineAdapter(mContext,reportBeans,this);
        recyclerReportOfMine.setLayoutManager(gridLayoutManager);
        recyclerReportOfMine.setAdapter(reportAdapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getReportOfMineSuccess(ReportListBean reportBeans) {
        if(swipeReportOfMine.isRefreshing()){
            swipeReportOfMine.setRefreshing(false);
        }
        this.reportBeans.clear();
        this.reportBeans.addAll(reportBeans);
        reportAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onReportClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportDetailActivity.class) ;
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void onGridFolderClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportFolderActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,Constants.REPORT_MINE);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }
}
