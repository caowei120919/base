package com.datacvg.dimp.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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
import com.datacvg.dimp.baseandroid.widget.dialog.CommentWindowDialog;
import com.datacvg.dimp.bean.CommentBean;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.event.DeleteCommentEvent;
import com.datacvg.dimp.event.RefreshTableEvent;
import com.datacvg.dimp.presenter.TableDetailPresenter;
import com.datacvg.dimp.view.TableDetailView;
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
    @BindView(R.id.webView)
    WebView webView ;

    private TableBean tableBean ;
    /**
     * 是否有参数选择
     *      默认没有
     */
    private boolean hasParamInfo = false ;
    private CommentWindowDialog commentDialog ;
    private Uri imageUri ;
    private File mTmpFile ;
    private List<String> imagePaths = new ArrayList<>();
    private List<CommentBean> commentBeans = new ArrayList<>();
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
        initWebView();
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tableBean = (TableBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(tableBean == null){
            finish();
            return;
        }
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
     * 初始化webview相关设置
     */
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        //若加载的html里有JS在执行动画等操作，会造成资源浪费（CPU、电量）在onStop和onResume
        //里分别把 setJavaScriptEnabled()给设置成false和true即可
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //缩放操作
        //webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面二个的前提。
        //webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //设置图片加载
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        //String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
        webSettings.setAppCacheEnabled(false);
        //设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        webSettings.setAllowContentAccess(false);
        //设置WebView是否保存表单数据，默认true，保存数据。
        webSettings.setSaveFormData(true);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        if (AndroidUtils.isNetworkAvailable()) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                int statusCode = errorResponse.getStatusCode();
                if(statusCode == 404 || statusCode == 500){
                    view.loadUrl("about:blank");
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (!TextUtils.isEmpty(powerBiToken)){
                    request.getRequestHeaders().put("authorization",powerBiToken);
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                PLog.e(url);
                super.onPageFinished(view, url);
            }
        });
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
                }
                break;

            case R.id.imgThree :
                PLog.e("默认报表被点击");
                break;
        }
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
//            getPresenter().upload(requestBodyMap);
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
            imgThree.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_default));
            imgTwo.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_report_comment));
            imgOne.setImageBitmap(BitmapFactory.decodeResource(resources
                    ,R.mipmap.icon_filter));
            hasParamInfo = true ;
        }else{
            imgThree.setVisibility(View.GONE);
            imgTwo.setImageBitmap(BitmapFactory.decodeResource(resources
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
                    webView.loadUrl(/*resdata.getShowUrl(),map*/url);
                    break;

                default:
                    webView.loadUrl(resdata.getShowUrl());
                    break;
            }
        }
    }

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
