package com.datacvg.dimp.fragment;

import android.Manifest;
import android.content.Intent;
import android.icu.text.Collator;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.ReportDetailActivity;
import com.datacvg.dimp.activity.ReportGridOnFolderActivity;
import com.datacvg.dimp.adapter.ReportGridOfMineAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.GlideLoader;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.MultipartUtil;
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
import com.datacvg.dimp.presenter.ReportOfTemplatePresenter;
import com.datacvg.dimp.view.ReportOfTemplateView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lcw.library.imagepicker.ImagePicker;
import com.mylhyl.superdialog.SuperDialog;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :管理画布模板
 */
public class ReportOfTemplateGridFragment extends BaseFragment<ReportOfTemplateView, ReportOfTemplatePresenter>
        implements ReportOfTemplateView, ReportGridOfMineAdapter.OnReportClickListener, OnRefreshListener {
    @BindView(R.id.swipe_reportOfTemplate)
    SmartRefreshLayout swipeReportOfTemplate ;
    @BindView(R.id.recycler_reportOfTemplate)
    RecyclerView recyclerReportOfTemplate ;

    public final static int TEMPLATE_REPORT_THUMB_REQUEST = 0x000003 ;
    private List<ReportBean> showReportBeans = new ArrayList<>() ;
    private List<ReportBean> originalBeans = new ArrayList<>() ;
    private List<ReportBean> sortBeans = new ArrayList<>() ;
    private ReportGridOfMineAdapter reportAdapter ;
    private GridLayoutManager gridLayoutManager ;
    private ReportBean reportBean ;
    private String changeAvatarPath ;
    private KProgressHUD mPDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_of_template;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        swipeReportOfTemplate.setEnableAutoLoadMore(false);
        swipeReportOfTemplate.setOnRefreshListener(this);
        swipeReportOfTemplate.setEnableRefresh(true);
        gridLayoutManager = new GridLayoutManager(mContext,2);
        reportAdapter = new ReportGridOfMineAdapter(mContext, Constants.REPORT_TEMPLATE
                , showReportBeans,this);
        recyclerReportOfTemplate.setLayoutManager(gridLayoutManager);
        recyclerReportOfTemplate.setAdapter(reportAdapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
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
        Intent intent = new Intent(mContext, ReportGridOnFolderActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,Constants.REPORT_TEMPLATE);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,Constants.REPORT_GRID);
        mContext.startActivity(intent);
    }

    @Override
    public void uploadThumb(ReportBean bean) {
        this.reportBean = bean ;
        choosePicture();
    }

    @Override
    public void addToScreen(ReportBean bean) {
        this.reportBean = bean ;
        ToastUtils.showLongToast("功能开发中,请敬请期待.......");
    }

    @Override
    public void deleteReport(ReportBean bean) {
        this.reportBean = bean ;
        List<String> listButton = new ArrayList<>();
        listButton.add(resources.getString(R.string.confirm_the_deletion));
        new SuperDialog.Builder(getActivity())
                .setCanceledOnTouchOutside(false)
                .setTitle(resources.getString(R.string.whether_to_delete_the_file),resources.getColor(R.color.c_303030),36,120)
                .setItems(listButton,resources.getColor(R.color.c_da3a16),36,120
                        , new SuperDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                getPresenter().deleteReport(bean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                            }
                        })
                .setNegativeButton(resources.getString(R.string.cancel)
                        ,resources.getColor(R.color.c_303030),36,120, null)
                .build();
    }

    @Override
    public void downloadReport(ReportBean bean) {
        this.reportBean = bean ;
        downloadFile();
        if(mPDialog == null){
            mPDialog = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDetailsLabel("下载中")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .setSize(120, 120);
        }
        mPDialog.show() ;
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

    private void choosePicture() {
        ImagePicker.getInstance()
                .setTitle(mContext.getResources().getString(R.string.select_picture))
                .setImageLoader(new GlideLoader())
                .showCamera(false)
                .showImage(true)
                .showVideo(false)
                .setSingleType(true)
                .setMaxCount(1)
                .start(getActivity(), TEMPLATE_REPORT_THUMB_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode) {
                case TEMPLATE_REPORT_THUMB_REQUEST :
                    changeAvatarPath = data.getStringArrayListExtra(ImagePicker
                            .EXTRA_SELECT_IMAGES).get(0) ;
                    compressThumb(changeAvatarPath);
                    break;
                default:
                    break;
            }
        }
    }

    private void compressThumb(String path) {
        File file = new File(path);
        if(file.getName().endsWith(".jpg") || file.getName().endsWith(".png")){
            try {
                Long fileSize = file.length();
                if(fileSize <= Constants.MAX_THUMB_SIZE){
                    upLoadAvatar(file);
                }else{
                    ToastUtils.showLongToast(resources.getString(R.string.the_image_size_cannot_exceed_5m));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ToastUtils.showLongToast(resources.getString(R.string.only_jpg_and_png_formats_are_supported));
        }
    }

    private void upLoadAvatar(File file) {
        Map<String, String> options = new HashMap<>();
        final Map<String, RequestBody> params = MultipartUtil.getRequestBodyMap(options, "img", file);
        getPresenter().uploadTemplateThumb(params,reportBean.getTemplate_id(),Constants.REPORT_TEMPLATE);
    }

    @Override
    public void getReportOfTemplateSuccess(ReportListBean data) {
        if(swipeReportOfTemplate.isRefreshing()){
            swipeReportOfTemplate.finishRefresh();
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
        reportAdapter.notifyDataSetChanged();
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
    public void uploadSuccess() {
        reportBean = null ;
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getReportSourceSuccess(String bean) {
        String mFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        String mFileName = "dimp_" + reportBean.getTemplate_id() + ".canvas";
        FileUtils.writeTxtToFile(bean,mFolder,mFileName);
        ToastUtils.showLongToast(resources.getString(R.string.download_successfully));
    }

    @Override
    public void deleteSuccess() {
        reportBean = null ;
        EventBus.getDefault().post(new ReportRefreshEvent());
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SortForSystemEvent event){
        showReportBeans.clear();
        showReportBeans.addAll(originalBeans);
        reportAdapter.notifyDataSetChanged();
        PLog.e("按系统排序");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SortForNameEvent event){
        showReportBeans.clear();
        showReportBeans.addAll(sortBeans);
        reportAdapter.notifyDataSetChanged();
        PLog.e("按名称排序");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RestoreSuccessEvent event){
        getPresenter().getReportOfTemplate(Constants.REPORT_TEMPLATE
                ,Constants.REPORT_TEMPLATE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }
}
