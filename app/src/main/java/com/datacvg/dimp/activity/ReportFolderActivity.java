package com.datacvg.dimp.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.icu.text.Collator;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.event.ReportRefreshEvent;
import com.datacvg.dimp.presenter.ReportFolderPresenter;
import com.datacvg.dimp.view.ReportFolderView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mylhyl.superdialog.SuperDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :管理画布文件夹详情
 */
public class ReportFolderActivity extends BaseActivity<ReportFolderView, ReportFolderPresenter>
        implements ReportFolderView, ReportListAdapter.OnReportListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_sort)
    ImageView imgRight ;
    @BindView(R.id.img_search)
    ImageView imgOther ;
    @BindView(R.id.recycler_reportOfFolder)
    RecyclerView recyclerReportOfFolder ;

    private ReportBean reportBean ;
    private List<ReportBean> showReportBeans = new ArrayList<>() ;
    private List<ReportBean> originalBeans = new ArrayList<>() ;
    private List<ReportBean> sortBeans = new ArrayList<>() ;
    private String folderType ;
    private ReportListAdapter adapter ;
    private String listType ;
    private PopupWindow sortPop ;
    private ReportBean selectedReportBean ;

    private KProgressHUD mPDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_folder;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));

        reportBean = (ReportBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        folderType = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        listType = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_ALBUM);
        if(reportBean == null || TextUtils.isEmpty(folderType) || TextUtils.isEmpty(listType)){
            finish();
            return;
        }
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_sort));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_search_of_report));
        switch (folderType){
            case Constants.REPORT_MINE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getModel_clname()
                        : reportBean.getModel_flname());
                break;

            case Constants.REPORT_SHARE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getShare_clname()
                        : reportBean.getShare_flname());
                break;

            case Constants.REPORT_TEMPLATE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getTemplate_clname()
                        : reportBean.getTemplate_flname());
                break;
        }

        adapter = new ReportListAdapter(mContext,folderType, showReportBeans,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerReportOfFolder.setLayoutManager(linearLayoutManager);
        recyclerReportOfFolder.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getModel_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_SHARE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getShare_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getTemplate_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    @OnClick({R.id.img_sort,R.id.img_search,R.id.img_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();
                break;

            case R.id.img_search :
                PLog.e("跳转到搜索");
                Intent intent = new Intent(mContext, SearchReportActivity.class) ;
                intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,(ReportBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN));
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,folderType);
                mContext.startActivity(intent);
                break;

            case R.id.img_sort :
                PLog.e("排序");
                if (sortPop == null){
                    createSortPopWindow();
                }else{
                    sortPop.showAsDropDown(imgOther,-150,20);
                }
                break;
        }
    }

    /**
     * 创建排序选择弹窗
     */
    private void createSortPopWindow() {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_sort, null);
        RelativeLayout relBySystemDefault = contentView.findViewById(R.id.rel_bySystemDefault);
        RelativeLayout relAccordingToTheName = contentView.findViewById(R.id.rel_accordingToTheName);
        relBySystemDefault.setOnClickListener(v -> {
            PLog.e("按系统排序");
            showReportBeans.clear();
            showReportBeans.addAll(originalBeans);
            adapter.notifyDataSetChanged();
            if(sortPop != null && sortPop.isShowing()){
                sortPop.dismiss();
            }
        });
        relAccordingToTheName.setOnClickListener(v -> {
            PLog.e("按名称排序");
            showReportBeans.clear();
            showReportBeans.addAll(sortBeans);
            adapter.notifyDataSetChanged();
            if(sortPop != null && sortPop.isShowing()){
                sortPop.dismiss();
            }
        });
        sortPop = new PopupWindow(contentView,
                (int) resources.getDimension(R.dimen.W260), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        sortPop.setTouchable(true);
        sortPop.setOutsideTouchable(false);
        sortPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
        sortPop.showAsDropDown(imgOther,-150,20);
    }

    @Override
    public void getReportSuccess(ReportListBean data) {
        this.originalBeans.addAll(data);
        showReportBeans.clear();
        showReportBeans.addAll(data);
        sortReportBeans();
        adapter.notifyDataSetChanged();
    }

    /**
     * 对报告进行排序操作
     */
    private void sortReportBeans() {
        sortBeans.clear();
        sortBeans.addAll(originalBeans);

        switch (folderType){
            case Constants.REPORT_MINE :
                Collections.sort(sortBeans, new Comparator<ReportBean>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(ReportBean o1, ReportBean o2) {
                        Comparator<Object> com = Collator.getInstance(Locale.CHINA);
                        if(LanguageUtils.isZh(mContext)){
                            return com.compare(o1.getModel_clname(),o2.getModel_clname());
                        }else{
                            return com.compare(o1.getModel_flname(),o2.getModel_flname());
                        }
                    }
                });
                break;

            case Constants.REPORT_SHARE :
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
                break;

            case Constants.REPORT_TEMPLATE :
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
                break;
        }
    }

    /**
     * 报告被删除
     * @param reportBean
     */
    @Override
    public void onReportDelete(ReportBean reportBean) {
        this.selectedReportBean = reportBean ;
        List<String> listButton = new ArrayList<>();
        listButton.add(resources.getString(R.string.confirm_the_deletion));
        new SuperDialog.Builder(mContext)
                .setCanceledOnTouchOutside(false)
                .setTitle(resources.getString(R.string.are_you_sure_to_clear_all_data),resources.getColor(R.color.c_303030),36,120)
                .setItems(listButton,resources.getColor(R.color.c_da3a16),36,120
                        , new SuperDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                switch (folderType){
                                    case Constants.REPORT_MINE :
                                        getPresenter().deleteReport(selectedReportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
                                        break;

                                    case Constants.REPORT_SHARE :
                                        getPresenter().deleteReport(selectedReportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                                        break;

                                    case Constants.REPORT_TEMPLATE :
                                        getPresenter().deleteReport(selectedReportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                                        break;
                                }
                            }
                        })
                .setNegativeButton(resources.getString(R.string.cancel)
                        ,resources.getColor(R.color.c_303030),36,120, null)
                .build();
    }

    @Override
    public void deleteSuccess() {
        ToastUtils.showLongToast(resources.getString(R.string.delete_the_success));
        EventBus.getDefault().post(new ReportRefreshEvent());
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getModel_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_SHARE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getShare_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getTemplate_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
        selectedReportBean = null ;
    }

    /**
     * 报告被添加到大屏
     * @param reportBean
     */
    @Override
    public void onReportAddToScreen(ReportBean reportBean) {
        this.selectedReportBean = reportBean ;
        this.selectedReportBean.setReport_type(folderType);
        Intent intent = new Intent(mContext, AddReportToScreenActivity.class) ;
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,this.selectedReportBean);
        mContext.startActivity(intent);
    }

    /**
     * 报告被下载
     * @param reportBean
     */
    @Override
    public void onReportDownload(ReportBean reportBean) {
        this.selectedReportBean = reportBean ;
        downloadFile();
        if(mPDialog != null){
            mPDialog.show();
        }else{
            mPDialog = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDetailsLabel("下载中")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .setSize(120, 120);
            mPDialog.show() ;
        }
    }

    private void downloadFile() {
        new RxPermissions(mContext).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtils.<Boolean>applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            switch (folderType){
                                case Constants.REPORT_MINE :
                                    getPresenter().downloadFile(selectedReportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
                                    break;

                                case Constants.REPORT_SHARE :
                                    getPresenter().downloadFile(selectedReportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                                    break;

                                case Constants.REPORT_TEMPLATE :
                                    getPresenter().downloadFile(selectedReportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                                    break;
                            }
                        } else {
                            ToastUtils.showShortToast(mContext.getResources()
                                    .getString(R.string.the_file_cannot_be_downloaded_because_the_permission_is_not_allowed));
                        }
                    }
                });
    }

    @Override
    public void getReportSourceSuccess(String bean) {
        String mFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        String mFileName = "dimp_" + selectedReportBean.getShare_id() + ".canvas";
        FileUtils.writeTxtToFile(bean,mFolder,mFileName);
        ToastUtils.showLongToast(resources.getString(R.string.download_successfully));
    }

    @Override
    public void onListFolderClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportFolderActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,folderType);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,listType);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void onReportClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportDetailActivity.class) ;
        reportBean.setReport_type(folderType);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }
}
