package com.datacvg.dimp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportGridOfTrashAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportTrashBean;
import com.datacvg.dimp.bean.ReportTrashListBean;
import com.datacvg.dimp.event.ClearAllReportEvent;
import com.datacvg.dimp.presenter.ReportOfTrashPresenter;
import com.datacvg.dimp.view.ReportOfTrashView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public class ReportOfTrashGridFragment extends BaseFragment<ReportOfTrashView, ReportOfTrashPresenter>
        implements ReportOfTrashView, ReportGridOfTrashAdapter.OnReportTrashClickListener, OnRefreshListener {
    @BindView(R.id.swipe_reportGridOfTrash)
    SmartRefreshLayout swipeReportGridOfTrash ;
    @BindView(R.id.recycler_reportGridOfTrash)
    RecyclerView recyclerReportGridOfTrash ;

    private List<ReportTrashBean>reportTrashBeans = new ArrayList<>() ;
    private ReportGridOfTrashAdapter adapter ;
    private ReportTrashBean reportTrashBean ;

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
        swipeReportGridOfTrash.setEnableAutoLoadMore(false);
        swipeReportGridOfTrash.setOnRefreshListener(this);
        swipeReportGridOfTrash.setEnableRefresh(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        adapter = new ReportGridOfTrashAdapter(mContext,this,reportTrashBeans);
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
    public void queryReportSuccess(ReportTrashListBean data) {
        if(swipeReportGridOfTrash.isRefreshing()){
            swipeReportGridOfTrash.finishRefresh();
        }
        reportTrashBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void restoreSuccess() {
        reportTrashBeans.clear();
        ToastUtils.showLongToast(resources.getString(R.string.restore_successful));
        queryReportOnTrash();
    }

    @Override
    public void deleteSuccess() {
        reportTrashBeans.clear();
        ToastUtils.showLongToast(resources.getString(R.string.delete_the_success));
        queryReportOnTrash();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClearAllReportEvent event){
        getPresenter().clearReport();
    }

    /**
     * 菜单栏点击处理
     * @param reportBean
     */
    @Override
    public void onMenuClick(ReportTrashBean reportBean) {
        this.reportTrashBean = reportBean ;
        showMenuDialog();
    }

    /**
     * 展示菜单弹窗
     */
    private void showMenuDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_report_trash_dialog
                ,null,false);
        RelativeLayout relDelete = containView.findViewById(R.id.rel_delete) ;
        RelativeLayout relRestore = containView.findViewById(R.id.rel_restore) ;

        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        Window window = alertDialog.getWindow();
        if(window != null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        relDelete.setOnClickListener(v -> {
            String type = "" ;
            switch (reportTrashBean.getRes_type()){
                case Constants.REPORT_TEMPLATE_TYPE :
                case Constants.REPORT_TEMPLATE_FOLDER_TYPE :
                    type = "TEMPLATE" ;
                    break;

                case Constants.REPORT_MINE_TYPE :
                case Constants.REPORT_MINE_FOLDER_TYPE :
                    type = "MODEL" ;
                    break;

                case Constants.REPORT_SHARE_TYPE :
                case Constants.REPORT_SHARE_FOLDER_TYPE :
                    type = "SHARE" ;
                    break;
            }
            getPresenter().deleteReportOnTrash(type,reportTrashBean.getRes_id());
            alertDialog.dismiss();
        });
        relRestore.setOnClickListener(v -> {
            String type = "" ;
            switch (reportTrashBean.getRes_type()){
                case Constants.REPORT_TEMPLATE_TYPE :
                case Constants.REPORT_TEMPLATE_FOLDER_TYPE :
                    type = "TEMPLATE" ;
                    break;

                case Constants.REPORT_MINE_TYPE :
                case Constants.REPORT_MINE_FOLDER_TYPE :
                    type = "MODEL" ;
                    break;

                case Constants.REPORT_SHARE_TYPE :
                case Constants.REPORT_SHARE_FOLDER_TYPE :
                    type = "SHARE" ;
                    break;
            }
            getPresenter().restoreOnTrash(type,reportTrashBean.getRes_id());
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        reportTrashBeans.clear();
        queryReportOnTrash();
    }
}
