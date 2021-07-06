package com.datacvg.dimp.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.GlideLoader;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.MultipartUtil;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.dimp.baseandroid.widget.dialog.CommentWindowDialog;
import com.datacvg.dimp.bean.CommentBean;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.ConstantReportBean;
import com.datacvg.dimp.bean.SetDefaultResBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.event.DeleteCommentEvent;
import com.datacvg.dimp.event.RefreshTableEvent;
import com.datacvg.dimp.presenter.TableDetailPresenter;
import com.datacvg.dimp.view.TableDetailView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.lcw.library.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-26
 * @Description : 报表详情
 */
public class TableDetailActivity extends BaseActivity<TableDetailView, TableDetailPresenter>
        implements TableDetailView, CommentWindowDialog.CommentViewClick {

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.imgOne)
    ImageView imgOne ;
    @BindView(R.id.imgTwo)
    ImageView imgTwo ;
    @BindView(R.id.imgThree)
    ImageView imgThree ;
    @BindView(R.id.container)
    RelativeLayout container ;

    private TableBean tableBean ;
    /**
     * 是否有参数选择
     *      默认没有
     */
    private boolean hasParamInfo = false ;
    private boolean isDefaultReport = false ;
    private CommentWindowDialog commentDialog ;
    private Uri imageUri ;
    private File mTmpFile ;
    private List<String> imagePaths = new ArrayList<>();
    private List<CommentBean> commentBeans = new ArrayList<>();
    protected AgentWeb mAgentWeb;
    /**
     * 报表参数选择筛选条件拼接
     */
    private String paramArr = "" ;
    /**
     * 报表评论参数拼接
     */
    private String params = "{}" ;
    private String powerBiToken = "" ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_table_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(this,mContext.getResources()
                .getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tableBean = (TableBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(tableBean == null){
            finish();
            return;
        }
        isDefaultReport = Constants.constantReportBean != null
                && Constants.constantReportBean.getResId().equals(tableBean.getRes_id()) ;
        tvTitle.setText(LanguageUtils.isZh(mContext)
                    ? tableBean.getRes_clname() : tableBean.getRes_flname());
        getResParamInfo();
        getPresenter().getTableUrl(paramArr,tableBean.getRes_id());
        getTableComments();
    }

    /**
     * 获取报表评论
     */
    private void getTableComments() {
        getPresenter().getTableComment(tableBean.getRes_id(),Uri.encode(params));
    }

    /**
     * 获取报表参数信息
     */
    private void getResParamInfo() {
        getPresenter().getResParamInfo(tableBean.getRes_id());
    }

    @OnClick({R.id.back,R.id.imgOne,R.id.imgTwo,R.id.imgThree})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back :
                    finish();
                break;

            case R.id.imgOne :
                /**
                 * 有参数选择  跳转
                 *      无参数选择   报表评论
                 */
                    if (hasParamInfo){
                        Intent intent = new Intent(mContext,SelectTableParamActivity.class);
                        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,tableBean);
                        mContext.startActivity(intent);
                    }else{
                        showCommentDistPlay();
                    }
                break;

            case R.id.imgTwo :
                /**
                 * 有参数选择  评论
                 *      无参数选择   默认报表
                 */
                if (hasParamInfo){
                    showCommentDistPlay();
                }else{
                    PLog.e("默认报表");
                    showSetDefaultDialog();
                }
                break;

            case R.id.imgThree :
                PLog.e("默认报表");
                showSetDefaultDialog();
                break;
        }
    }

    /**
     * 设置默认报表
     */
    private void showSetDefaultDialog() {
        CVGOKCancelWithTitle dialogOKCancel = new CVGOKCancelWithTitle(mContext);
        dialogOKCancel.setMessage(isDefaultReport ? resources.getString(R.string.cancel_the_current_report_as_default)
                : resources.getString(R.string.set_the_current_report_to_default));
        dialogOKCancel.getPositiveButton().setText(mContext.getResources().getString(R.string.confirm));
        dialogOKCancel.setOnClickPositiveButtonListener(view -> {
            if(isDefaultReport){
                getPresenter().cancelReportForDefault(Constants.constantReportBean.getPKId());
            }else{
                Map map = new HashMap();
                map.put("resId",tableBean.getRes_id());
                map.put("rootId",tableBean.getRes_rootid());
                map.put("mobileType","app");
                getPresenter().setReportToDefault(map);
            }
        });
        dialogOKCancel.setOnClickListenerNegativeBtn(view -> {

        });
        dialogOKCancel.show();
    }

    /**
     * 报表评论展示
     */
    private void showCommentDistPlay() {
        if(commentDialog != null){
            commentDialog.showDialog() ;
        }else{
            commentDialog = new CommentWindowDialog(mContext,imagePaths,this,commentBeans)
                    .backgroundLight(0.2)
                    .fromBottom()
                    .showDialog()
                    .setWidthAndHeight(getWindowManager().getDefaultDisplay().getWidth()
                            , (int) resources.getDimension(R.dimen.H850))
                    .setCancelAble(true)
                    .setCanceledOnTouchOutside(false);
        }
    }


    /**
     * 打开相册
     */
    @Override
    public void openAlbum() {
        ImagePicker.getInstance()
                .setTitle(mContext.getResources().getString(R.string.select_picture))
                .showCamera(false)
                .showImage(true)
                .setSingleType(true)
                .setMaxCount(6 - imagePaths.size())
                .setImageLoader(new GlideLoader())
                .start(mContext, Constants.REQUEST_OPEN_CAMERA);
    }

    @Override
    public void sendComments(String mComments, List<String> imagePaths) {
        try {
            List<File> imgFiles = new ArrayList<>();
            for (int i =0;i < imagePaths.size() ; i++){
                imgFiles.add(Luban.with(mContext).load(imagePaths).get().get(i).getAbsoluteFile());
            }
            Map params = new HashMap();
            params.put("commentPkid","");
            params.put("commentUserId",PreferencesHelper.get(Constants.USER_ID,""));
            params.put("resId",tableBean.getRes_id());
            params.put("parentId","0");
            params.put("resClname", tableBean.getRes_clname());
            params.put("text",mComments);
            params.put("remindedUsers","");
            params.put("params","{}");
            params.put("content",mComments);
            final  Map<String, RequestBody> requestBodyMap = MultipartUtil.getRequestBodyMap(params,"files",imgFiles);
            getPresenter().submitComments(requestBodyMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     */
    @Override
    public void takeCamera() {
        new RxPermissions(mContext)
                .request(Manifest.permission.CAMERA
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>(){
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean){      //授权通过拍摄照片
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (cameraIntent.resolveActivity(mContext.getPackageManager()) != null){
                                mTmpFile = FileUtils.createTmpFile(mContext);
                                //通过FileProvider创建一个content类型的Uri
                                imageUri = FileProvider.getUriForFile(mContext
                                        , "com.datacvg.dimp.fileprovider", mTmpFile);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                mContext.startActivityForResult(cameraIntent, Constants.REQUEST_TAKE_PHOTO);
                            }
                        }else{
                            ToastUtils.showLongToast(mContext.getResources()
                                    .getString(R.string.dont_allow_permissions));
                        }
                    }
                });
    }

    /**
     * 获取报表参数成功
     * @param tableParamInfoListBean
     *         不为空并且有维度或者参数选择，则允许跳转到选择页面
     */
    @Override
    public void getParamInfoSuccess(TableParamInfoListBean tableParamInfoListBean) {
        if(tableParamInfoListBean != null && tableParamInfoListBean.size() > 0){
            imgThree.setImageBitmap(isDefaultReport ? BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_normal) : BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_default));
            imgTwo.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_comment));
            imgOne.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_filter));
            hasParamInfo = true ;
        }else{
            imgThree.setVisibility(View.GONE);
            imgTwo.setImageBitmap(isDefaultReport ? BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_normal) : BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_default));
            imgOne.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_comment));
            hasParamInfo = false ;
        }
    }

    /**
     * 获取报表配置信息成功
     * @param resdata
     */
    @Override
    public void getTableInfoSuccess(TableInfoBean resdata) {
        if(resdata != null && !TextUtils.isEmpty(resdata.getShowUrl())){
            switch (resdata.getReportType()){
                case "powerbi" :
                    Map<String,String> map = new HashMap<>();
                    map.put("authorization",resdata.getData().getToken());
                    powerBiToken = resdata.getData().getToken() ;
//                    getPresenter().getPowerBiInfo(powerBiToken);
                    String url = "file:///android_asset/powerbi/index.html";
                    mAgentWeb = AgentWeb.with(this)
                            .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                            .useDefaultIndicator()
                            .setWebChromeClient(mWebChromeClient)
                            .setWebViewClient(mWebViewClient)
                            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                            .createAgentWeb()
                            .ready()
                            .go(url);
                    break;

                case "MODEL" :

                    break;

                default:
                    mAgentWeb = AgentWeb.with(this)
                            .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                            .useDefaultIndicator()
                            .setWebChromeClient(mWebChromeClient)
                            .setWebViewClient(mWebViewClient)
                            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                            .createAgentWeb()
                            .ready()
                            .go(resdata.getShowUrl());
                    break;
            }
        }
    }

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            PLog.e(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            PLog.e(url);
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return true;
        }



        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            PLog.e(view.getUrl());
            super.onProgressChanged(view, newProgress);
        }
    };

    /**
     * 评论成功回调
     */
    @Override
    public void submitCommentsSuccess() {
        getTableComments();
        imagePaths.clear();
        commentDialog.submitSuccess();
    }

    /**
     * 获取报表评论成功
     * @param resdata
     */
    @Override
    public void getCommentsSuccess(CommentListBean resdata) {
        commentBeans.clear();
        commentBeans.addAll(resdata.getCommentInfoList());
        if(commentDialog != null){
            commentDialog.refreshComment(commentBeans);
        }
    }

    /**
     * 设置默认报表成功
     * @param baseBean
     */
    @Override
    public void setDefaultReportSuccess(SetDefaultResBean baseBean) {
        Constants.constantReportBean = new ConstantReportBean(tableBean.getRes_id()
                ,baseBean.getDefaultPkid());
        isDefaultReport = true ;
        if(imgThree.getVisibility() == View.VISIBLE){
            imgThree.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_normal));
        }else{
            imgTwo.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_normal));
        }
    }

    /**
     * 取消默认报表成功
     */
    @Override
    public void cancelDefaultReportSuccess() {
        Constants.constantReportBean = null;
        isDefaultReport = false ;
        if(imgThree.getVisibility() == View.VISIBLE){
            imgThree.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_default));
        }else{
            imgTwo.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_default));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshTableEvent event){
        getPresenter().getTableUrl(event.getParamArr(),tableBean.getRes_id());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeleteCommentEvent event){
        if (event.isDeleteAll()){
            imagePaths.clear();
            commentDialog.setPicturePath(imagePaths);
            return;
        }
        imagePaths.remove(event.getPosition());
        commentDialog.setPicturePath(imagePaths);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode) {
                case Constants.REQUEST_TAKE_PHOTO :
                    imagePaths.add(mTmpFile.getAbsolutePath());
                    break;

                default :
                    List<String> images = data.getStringArrayListExtra(ImagePicker
                            .EXTRA_SELECT_IMAGES);
                    imagePaths.addAll(images);
                    break;
            }
        }
        commentDialog.setPicturePath(imagePaths);
    }
}
