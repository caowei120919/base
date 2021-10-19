package com.datacvg.dimp.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
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
import com.datacvg.dimp.activity.ReportDetailActivity;
import com.datacvg.dimp.activity.ReportFolderActivity;
import com.datacvg.dimp.adapter.ReportGridOfMineAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.GlideLoader;
import com.datacvg.dimp.baseandroid.utils.MultipartUtil;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.ReportOfMinePresenter;
import com.datacvg.dimp.view.ReportOfMineView;
import com.lcw.library.imagepicker.ImagePicker;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import static android.app.Activity.RESULT_OK;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description : 管理画布我的
 */
public class ReportOfMineGridFragment extends BaseFragment<ReportOfMineView, ReportOfMinePresenter>
        implements ReportOfMineView, ReportGridOfMineAdapter.OnReportClickListener, OnRefreshListener {
    @BindView(R.id.swipe_reportOfMine)
    SmartRefreshLayout swipeReportOfMine ;
    @BindView(R.id.recycler_reportOfMine)
    RecyclerView recyclerReportOfMine ;

    public final static int MINE_REPORT_THUMB_REQUEST = 0x000001 ;
    private String changeAvatarPath;
    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private ReportGridOfMineAdapter reportAdapter ;
    private GridLayoutManager gridLayoutManager ;
    private ReportBean reportBean ;

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
        swipeReportOfMine.setEnableAutoLoadMore(false);
        swipeReportOfMine.setOnRefreshListener(this);
        swipeReportOfMine.setEnableRefresh(true);
        gridLayoutManager = new GridLayoutManager(mContext,2);
        reportAdapter = new ReportGridOfMineAdapter(mContext,Constants.REPORT_MINE,reportBeans,this);
        recyclerReportOfMine.setLayoutManager(gridLayoutManager);
        recyclerReportOfMine.setAdapter(reportAdapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_MINE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getReportOfMineSuccess(ReportListBean reportBeans) {
        if(swipeReportOfMine.isRefreshing()){
            swipeReportOfMine.finishRefresh();
        }
        this.reportBeans.clear();
        this.reportBeans.addAll(reportBeans);
        reportAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadSuccess() {
        reportBean = null ;
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_MINE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 获取报告成功,并生成文件
     * @param bean
     */
    @Override
    public void getReportSourceSuccess(String bean) {
        String mFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        String mFileName = "dimp_" + reportBean.getModel_id() + ".canvas";
        FileUtils.writeTxtToFile(bean,mFolder,mFileName);
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

    /**
     * 菜单点击监听
     * @param reportBean
     */
    @Override
    public void onMenuClick( ReportBean reportBean) {
        this.reportBean = reportBean ;
        showMenuDialog();
    }

    private void showMenuDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_report_grid_dialog
                ,null,false);
        RelativeLayout relUploadThumb = containView.findViewById(R.id.rel_uploadThumb) ;
        RelativeLayout relAddScreen = containView.findViewById(R.id.rel_addScreen) ;
        RelativeLayout relDelete = containView.findViewById(R.id.rel_delete) ;
        RelativeLayout relDownLoad = containView.findViewById(R.id.rel_downLoad) ;

        relAddScreen.setVisibility(!reportBean.getFolder() ?  View.VISIBLE : View.GONE);
        relDownLoad.setVisibility((reportBean.isEditAble() && !reportBean.getFolder()) ? View.VISIBLE : View.GONE);
        relDelete.setVisibility(reportBean.isEditAble() ? View.VISIBLE : View.GONE );
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        Window window = alertDialog.getWindow();
        if(window != null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        relUploadThumb.setOnClickListener(v -> {
            PLog.e("上传缩略图");
            choosePicture();
            alertDialog.dismiss();
        });
        relAddScreen.setOnClickListener(v -> {
            PLog.e("添加到大屏");
            ToastUtils.showLongToast("功能开发中,请敬请期待.......");
            alertDialog.dismiss();
        });
        relDelete.setOnClickListener(v -> {
            PLog.e("删除报告");
            alertDialog.dismiss();
        });
        relDownLoad.setOnClickListener(v -> {
            PLog.e("下载报告");
            downloadFile();
            alertDialog.dismiss();
        });
        alertDialog.show();
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
                            getPresenter().downloadFile(reportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
                        } else {
                            ToastUtils.showShortToast(mContext.getResources()
                                    .getString(R.string.the_file_cannot_be_downloaded_because_the_permission_is_not_allowed));
                        }
                    }
                });
    }

    /**
     * 选择缩略图
     */
    private void choosePicture() {
        ImagePicker.getInstance()
                .setTitle(mContext.getResources().getString(R.string.select_picture))
                .setImageLoader(new GlideLoader())
                .showCamera(false)
                .showImage(true)
                .showVideo(false)
                .setSingleType(true)
                .setMaxCount(1)
                .start(getActivity(), MINE_REPORT_THUMB_REQUEST);
    }

    private void compressThumb(String path) {
        Luban.with(mContext)
                .load(path)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        upLoadAvatar(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode) {
                case MINE_REPORT_THUMB_REQUEST :
                    changeAvatarPath = data.getStringArrayListExtra(ImagePicker
                            .EXTRA_SELECT_IMAGES).get(0) ;
                    compressThumb(changeAvatarPath);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }

    private void upLoadAvatar(File file) {
        Map<String, String> options = new HashMap<>();
        final Map<String, RequestBody> params = MultipartUtil.getRequestBodyMap(options, "img", file);
        getPresenter().uploadModelThumb(params,reportBean.getModel_id(),Constants.REPORT_MINE);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getReportOfMine(Constants.REPORT_MINE
                ,Constants.REPORT_MINE_PARENT_ID
                ,String.valueOf(System.currentTimeMillis()));
    }
}
