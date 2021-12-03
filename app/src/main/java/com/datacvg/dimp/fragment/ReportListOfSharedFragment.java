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
import com.datacvg.dimp.activity.AddReportToScreenActivity;
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
import com.datacvg.dimp.event.RestoreSuccessEvent;
import com.datacvg.dimp.event.SortForNameEvent;
import com.datacvg.dimp.event.SortForSystemEvent;
import com.datacvg.dimp.presenter.ReportListOfSharedPresenter;
import com.datacvg.dimp.view.ReportListOfSharedView;
import com.mylhyl.superdialog.SuperDialog;
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
 * @Description : 共享管理画布列表
 */
public class ReportListOfSharedFragment extends BaseFragment<ReportListOfSharedView, ReportListOfSharedPresenter>
        implements ReportListOfSharedView, ReportListAdapter.OnReportListener, OnRefreshListener {
    @BindView(R.id.swipe_reportListOfShare)
    SmartRefreshLayout swipeReportListOfShare ;
    @BindView(R.id.recycler_reportListOfShare)
    RecyclerView recyclerReportListOfShare ;

    private List<ReportBean> showReportBeans = new ArrayList<>() ;
    private List<ReportBean> originalBeans = new ArrayList<>() ;
    private List<ReportBean> sortBeans = new ArrayList<>() ;
    private ReportListAdapter adapter ;
    private ReportBean reportBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_shared_list;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        swipeReportListOfShare.setEnableAutoLoadMore(false);
        swipeReportListOfShare.setOnRefreshListener(this);
        swipeReportListOfShare.setEnableRefresh(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new ReportListAdapter(mContext, Constants.REPORT_SHARE, showReportBeans,this);
        recyclerReportListOfShare.setLayoutManager(linearLayoutManager);
        recyclerReportListOfShare.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportOfShare(Constants.REPORT_SHARE
                ,Constants.REPORT_SHARE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getReportOfShareSuccess(ReportListBean data) {
        if(swipeReportListOfShare.isRefreshing()){
            swipeReportListOfShare.finishRefresh();
        }
        this.originalBeans.clear();
        for (ReportBean bean : data){
            if(!bean.getShare_id().equals(Constants.REPORT_SHARE_PARENT_ID)){
                this.originalBeans.add(bean);
            }
        }
        sortReportBeans() ;
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
                    return com.compare(o1.getShare_clname(),o2.getShare_clname());
                }else{
                    return com.compare(o1.getShare_flname(),o2.getShare_flname());
                }
            }
        });
    }

    @Override
    public void deleteSuccess() {
        EventBus.getDefault().post(new ReportRefreshEvent());
        for (ReportBean reportBean : showReportBeans){
            if(reportBean.getShare_id().equals(this.reportBean.getShare_id())){
                showReportBeans.remove(reportBean);
                adapter.notifyDataSetChanged();
                return;
            }
        }
        reportBean = null ;
    }

    @Override
    public void getReportSourceSuccess(String bean) {
        String mFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        String mFileName = "dimp_" + reportBean.getShare_id() + ".canvas";
        FileUtils.writeTxtToFile(bean,mFolder,mFileName);
        ToastUtils.showLongToast(resources.getString(R.string.download_successfully));
    }

    /**
     * 报告被删除
     * @param reportBean
     */
    @Override
    public void onReportDelete(ReportBean reportBean) {
        this.reportBean = reportBean ;
        List<String> listButton = new ArrayList<>();
        listButton.add(resources.getString(R.string.confirm_the_deletion));
        new SuperDialog.Builder(getActivity())
                .setCanceledOnTouchOutside(false)
                .setTitle(resources.getString(R.string.are_you_sure_to_clear_all_data),resources.getColor(R.color.c_303030),24,80)
                .setItems(listButton,resources.getColor(R.color.c_da3a16),24,80
                        , new SuperDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                getPresenter().deleteReport(reportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                            }
                        })
                .setNegativeButton(resources.getString(R.string.cancel)
                        ,resources.getColor(R.color.c_303030),24,80, null)
                .build();
    }

    /**
     * 报告被添加到大屏
     * @param reportBean
     */
    @Override
    public void onReportAddToScreen(ReportBean reportBean) {
        this.reportBean = reportBean ;
        this.reportBean.setReport_type(Constants.REPORT_SHARE);
        Intent intent = new Intent(mContext, AddReportToScreenActivity.class) ;
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,this.reportBean);
        mContext.startActivity(intent);
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

    private void downloadFile() {
        new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtils.<Boolean>applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            getPresenter().downloadFile(reportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                        } else {
                            ToastUtils.showShortToast(mContext.getResources()
                                    .getString(R.string.the_file_cannot_be_downloaded_because_the_permission_is_not_allowed));
                        }
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getReportOfShare(Constants.REPORT_SHARE
                ,Constants.REPORT_SHARE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onListFolderClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportFolderActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,Constants.REPORT_SHARE);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,Constants.REPORT_LIST);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void onReportClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportDetailActivity.class) ;
        reportBean.setReport_type(Constants.REPORT_SHARE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RestoreSuccessEvent event){
        getPresenter().getReportOfShare(Constants.REPORT_SHARE
                ,Constants.REPORT_SHARE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }
}
