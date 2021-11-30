package com.datacvg.dimp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

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
    ImageView imgSearch;
    @BindView(R.id.recycler_report)
    RecyclerView recyclerReportOfFolder ;
    @BindView(R.id.img_other)
    ImageView imgOther ;

    private ReportBean reportBean ;
    private ReportBean chooseReportBean ;
    private List<ReportBean> showReportBeans = new ArrayList<>() ;
    private List<ReportBean> originalBeans = new ArrayList<>() ;
    private List<ReportBean> sortBeans = new ArrayList<>() ;
    private String folderType ;
    private ReportGridOfMineAdapter gridAdapter ;
    private String listType ;
    private PopupWindow sortPop ;
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
        imgSearch.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_search_of_report));
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
        gridAdapter = new ReportGridOfMineAdapter(mContext,folderType, showReportBeans,this);
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
                if (sortPop == null){
                    createSortPopWindow();
                }else{
                    sortPop.showAsDropDown(imgOther,-150,20);
                }
                break;
        }
    }

    @Override
    public void getReportSuccess(ReportListBean data) {
        this.originalBeans.clear();
        this.originalBeans.addAll(data);
        showReportBeans.clear();
        showReportBeans.addAll(data);
        sortReportBeans();
        gridAdapter.notifyDataSetChanged();
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
            gridAdapter.notifyDataSetChanged();
            if(sortPop != null && sortPop.isShowing()){
                sortPop.dismiss();
            }
        });
        relAccordingToTheName.setOnClickListener(v -> {
            PLog.e("按名称排序");
            showReportBeans.clear();
            showReportBeans.addAll(sortBeans);
            gridAdapter.notifyDataSetChanged();
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
        chooseReportBean = null ;
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
        chooseReportBean = null ;
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().getReportOnFolder(Constants.REPORT_MINE
                        ,reportBean.getModel_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_SHARE :
                getPresenter().getReportOnFolder(Constants.REPORT_SHARE
                        ,reportBean.getShare_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().getReportOnFolder(Constants.REPORT_TEMPLATE
                        ,reportBean.getTemplate_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    @Override
    public void onReportClick(ReportBean reportBean) {
        Intent intent = new Intent(mContext, ReportDetailActivity.class) ;
        reportBean.setReport_type(folderType);
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
        this.chooseReportBean = bean ;
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
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().uploadModelThumb(params,chooseReportBean.getModel_id(),Constants.REPORT_MINE);
                break;

            case Constants.REPORT_SHARE :
                getPresenter().uploadModelThumb(params,chooseReportBean.getShare_id(),Constants.REPORT_SHARE);
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().uploadModelThumb(params,chooseReportBean.getTemplate_id(),Constants.REPORT_TEMPLATE);
                break;
        }
    }

    @Override
    public void addToScreen(ReportBean bean) {
        this.chooseReportBean = bean ;
        this.chooseReportBean.setReport_type(folderType);
        Intent intent = new Intent(mContext, AddReportToScreenActivity.class) ;
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,this.chooseReportBean);
        mContext.startActivity(intent);
    }

    @Override
    public void deleteReport(ReportBean bean) {
        this.chooseReportBean = bean ;
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().deleteReport(chooseReportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
                break;

            case Constants.REPORT_SHARE :
                getPresenter().deleteReport(chooseReportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().deleteReport(chooseReportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
                break;
        }
    }

    @Override
    public void downloadReport(ReportBean bean) {
        this.chooseReportBean = bean ;
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
                                    getPresenter().downloadFile(chooseReportBean.getModel_id(),Constants.REPORT_MINE_TYPE);
                                    break;

                                case Constants.REPORT_SHARE :
                                    getPresenter().downloadFile(chooseReportBean.getShare_id(),Constants.REPORT_SHARE_TYPE);
                                    break;

                                case Constants.REPORT_TEMPLATE :
                                    getPresenter().downloadFile(chooseReportBean.getTemplate_id(),Constants.REPORT_TEMPLATE_TYPE);
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
