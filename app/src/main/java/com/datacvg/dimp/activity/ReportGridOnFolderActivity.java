package com.datacvg.dimp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportGridOfMineAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.GlideLoader;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.MultipartUtil;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.event.ReportRefreshEvent;
import com.datacvg.dimp.presenter.ReportGridOnFolderPresenter;
import com.datacvg.dimp.view.ReportGridOnFolderView;
import com.lcw.library.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-27
 * @Description :
 */
public class ReportGridOnFolderActivity extends BaseActivity<ReportGridOnFolderView, ReportGridOnFolderPresenter>
        implements ReportGridOnFolderView, ReportGridOfMineAdapter.OnReportClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_sort)
    ImageView imgRight ;
    @BindView(R.id.img_search)
    ImageView imgOther ;
    @BindView(R.id.recycler_report)
    RecyclerView recyclerReportOfFolder ;

    private ReportBean reportBean ;
    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private String folderType ;
    private ReportGridOfMineAdapter gridAdapter ;
    private String listType ;
    private String changeAvatarPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_folder_on_grid;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
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
        gridAdapter = new ReportGridOfMineAdapter(mContext,folderType,reportBeans,this);
        recyclerReportOfFolder.setLayoutManager(gridLayoutManager);
        recyclerReportOfFolder.setAdapter(gridAdapter);
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
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,folderType);
                mContext.startActivity(intent);
                break;

            case R.id.img_sort :
                PLog.e("排序");
                break;
        }
    }

    @Override
    public void getReportSuccess(ReportListBean data) {
        reportBeans.clear();
        reportBeans.addAll(data);
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void getReportSourceSuccess(String bean) {
        String mFolder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
        String mFileName = "dimp_" + reportBean.getModel_id() + ".canvas";
        FileUtils.writeTxtToFile(bean,mFolder,mFileName);
    }

    @Override
    public void deleteSuccess() {
        reportBean = null ;
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
    }

    @Override
    public void uploadSuccess() {
        reportBean = null ;
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().getReportOfMine(Constants.REPORT_MINE
                        ,Constants.REPORT_MINE_PARENT_ID
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_SHARE :
                getPresenter().getReportOfMine(Constants.REPORT_SHARE
                        ,Constants.REPORT_SHARE_PARENT_ID
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().getReportOfMine(Constants.REPORT_TEMPLATE
                        ,Constants.REPORT_TEMPLATE_PARENT_ID
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    @Override
    public void getReportOfMineSuccess(ReportListBean data) {
        this.reportBeans.clear();
        this.reportBeans.addAll(reportBeans);
        gridAdapter.notifyDataSetChanged();
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
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,folderType);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,listType);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void uploadThumb(ReportBean bean) {
        this.reportBean = bean ;
        choosePicture();
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
                .start(mContext, Constants.REQUEST_OPEN_CAMERA);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode) {
                case Constants.REQUEST_OPEN_CAMERA :
                    changeAvatarPath = data.getStringArrayListExtra(ImagePicker
                            .EXTRA_SELECT_IMAGES).get(0) ;
                    compressThumb(changeAvatarPath);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
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

    private void upLoadAvatar(File file) {
        Map<String, String> options = new HashMap<>();
        final Map<String, RequestBody> params = MultipartUtil.getRequestBodyMap(options, "img", file);
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().uploadModelThumb(params,reportBean.getModel_id(),Constants.REPORT_MINE);
                break;

            case Constants.REPORT_SHARE :
                getPresenter().uploadModelThumb(params,reportBean.getModel_id(),Constants.REPORT_SHARE);
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().uploadModelThumb(params,reportBean.getModel_id(),Constants.REPORT_TEMPLATE);
                break;
        }
    }

    @Override
    public void addToScreen(ReportBean bean) {
        this.reportBean = bean ;
        this.reportBean.setReport_type(folderType);
        Intent intent = new Intent(mContext, AddReportToScreenActivity.class) ;
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,this.reportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void deleteReport(ReportBean bean) {
        this.reportBean = bean ;
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().deleteReport(bean.getModel_id(),Constants.REPORT_MINE_TYPE);
                break;

            case Constants.REPORT_SHARE :
                getPresenter().deleteReport(bean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().deleteReport(bean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                break;
        }
    }

    @Override
    public void downloadReport(ReportBean bean) {
        this.reportBean = bean ;
        downloadFile();
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        new RxPermissions(mContext).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            switch (folderType){
                                case Constants.REPORT_MINE :
                                    getPresenter().downloadFile(reportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
                                    break;

                                case Constants.REPORT_SHARE :
                                    getPresenter().downloadFile(reportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                                    break;

                                case Constants.REPORT_TEMPLATE :
                                    getPresenter().downloadFile(reportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                                    break;
                            }
                        } else {
                            ToastUtils.showShortToast(mContext.getResources()
                                    .getString(R.string.the_file_cannot_be_downloaded_because_the_permission_is_not_allowed));
                        }
                    }
                });
    }
}
