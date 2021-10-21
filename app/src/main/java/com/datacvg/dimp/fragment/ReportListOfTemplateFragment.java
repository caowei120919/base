package com.datacvg.dimp.fragment;

import android.Manifest;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.event.ReportRefreshEvent;
import com.datacvg.dimp.event.SortForSystemEvent;
import com.datacvg.dimp.presenter.ReportListOfTemplatePresenter;
import com.datacvg.dimp.view.ReportListOfTemplateView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private ReportBean reportBean ;

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

    @Override
    public void deleteSuccess() {
        reportBean = null ;
        EventBus.getDefault().post(new ReportRefreshEvent());
        for (ReportBean reportBean : reportBeans){
            if(reportBean.getTemplate_id().equals(this.reportBean.getTemplate_id())){
                reportBeans.remove(reportBean);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 报告资源获取成功
     * @param bean
     */
    @Override
    public void getReportSourceSuccess(String bean) {
        String mFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        String mFileName = "dimp_" + reportBean.getTemplate_id() + ".canvas";
        FileUtils.writeTxtToFile(bean,mFolder,mFileName);
    }

    /**
     * 报告被删除
     * @param reportBean
     */
    @Override
    public void onReportDelete(ReportBean reportBean) {
        this.reportBean = reportBean ;
        getPresenter().deleteReport(reportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
    }

    /**
     * 报告被添加到大屏
     * @param reportBean
     */
    @Override
    public void onReportAddToScreen(ReportBean reportBean) {
        this.reportBean = reportBean ;
        PLog.e("功能开发中,请敬请期待......");
    }

    /**
     * 报告被下载
     * @param reportBean
     */
    @Override
    public void onReportDownload(ReportBean reportBean) {
        this.reportBean = reportBean ;
        downloadFile();
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            getPresenter().downloadFile(reportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                        } else {
                            ToastUtils.showShortToast(mContext.getResources()
                                    .getString(R.string.the_file_cannot_be_downloaded_because_the_permission_is_not_allowed));
                        }
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SortForSystemEvent event){
        PLog.e("按系统排序");
    }
}
