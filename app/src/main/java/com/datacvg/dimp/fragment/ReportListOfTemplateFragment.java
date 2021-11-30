package com.datacvg.dimp.fragment;

import android.Manifest;
import android.content.Intent;
import android.icu.text.Collator;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.ReportDetailActivity;
import com.datacvg.dimp.activity.ReportFolderActivity;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.event.ReportRefreshEvent;
import com.datacvg.dimp.event.SortForNameEvent;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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

    private List<ReportBean> showReportBeans = new ArrayList<>();
    private List<ReportBean> originalBeans = new ArrayList<>() ;
    private List<ReportBean> sortBeans = new ArrayList<>() ;
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
        adapter = new ReportListAdapter(mContext, Constants.REPORT_TEMPLATE, showReportBeans,this);
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
        this.originalBeans.clear();
        for (ReportBean bean : data){
            if(!bean.getTemplate_id().equals(Constants.REPORT_TEMPLATE_PARENT_ID)){
                this.originalBeans.add(bean);
            }
        }
        sortReportBeans();
        this.showReportBeans.clear();
        this.showReportBeans.addAll(originalBeans);
        adapter.notifyDataSetChanged();
    }

    /**
     * 对报告进行排序操作
     */
    private void sortReportBeans() {
        sortBeans.clear();
        sortBeans.addAll(originalBeans);
        Collections.sort(sortBeans, new Comparator<ReportBean>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public int compare(ReportBean o1, ReportBean o2) {
                Comparator<Object> com = Collator.getInstance(Locale.CHINA);
                if(LanguageUtils.isZh(mContext)){
                    return com.compare(o1.getTemplate_clname(),o2.getTemplate_clname());
                }else{
                    return com.compare(o1.getTemplate_flname(),o2.getTemplate_flname());
                }
            }
        });
    }

    @Override
    public void deleteSuccess() {
        reportBean = null ;
        EventBus.getDefault().post(new ReportRefreshEvent());
        for (ReportBean reportBean : showReportBeans){
            if(reportBean.getTemplate_id().equals(this.reportBean.getTemplate_id())){
                showReportBeans.remove(reportBean);
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
        CVGOKCancelWithTitle dialogOKCancel = new CVGOKCancelWithTitle(mContext);
        dialogOKCancel.setMessage(mContext.getResources()
                .getString(R.string.confirm_deletion));
        dialogOKCancel.setCancelable(false);
        dialogOKCancel.setOnClickPositiveButtonListener(view -> {
            getPresenter().deleteReport(reportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
        });
        dialogOKCancel.setOnClickListenerNegativeBtn(view -> {
            dialogOKCancel.dismiss();
        });
        dialogOKCancel.show();
    }

    /**
     * 报告被添加到大屏
     * @param reportBean
     */
    @Override
    public void onReportAddToScreen(ReportBean reportBean) {
        this.reportBean = reportBean ;
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

    @Override
    public void onListFolderClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportFolderActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,Constants.REPORT_TEMPLATE);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,Constants.REPORT_LIST);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void onReportClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportDetailActivity.class) ;
        reportBean.setReport_type(Constants.REPORT_TEMPLATE);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SortForSystemEvent event){
        showReportBeans.clear();
        showReportBeans.addAll(originalBeans);
        adapter.notifyDataSetChanged();
        PLog.e("按系统排序");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SortForNameEvent event){
        showReportBeans.clear();
        showReportBeans.addAll(sortBeans);
        adapter.notifyDataSetChanged();
        PLog.e("按名称排序");
    }
}
