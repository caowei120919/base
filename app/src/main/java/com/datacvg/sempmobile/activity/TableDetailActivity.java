package com.datacvg.sempmobile.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.datacvg.sempmobile.baseandroid.utils.LanguageUtils;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.bean.TableBean;
import com.datacvg.sempmobile.bean.TableInfoBean;
import com.datacvg.sempmobile.bean.TableParamInfoBean;
import com.datacvg.sempmobile.bean.TableParamInfoListBean;
import com.datacvg.sempmobile.event.RefreshTableEvent;
import com.datacvg.sempmobile.presenter.TableDetailPresenter;
import com.datacvg.sempmobile.view.TableDetailView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-26
 * @Description : 报表详情
 */
public class TableDetailActivity extends BaseActivity<TableDetailView, TableDetailPresenter>
        implements TableDetailView {

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
        getPresenter().getTableUrl("",tableBean.getRes_id());
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

                    }
                break;

            case R.id.imgTwo :
                /**
                 * 有参数选择  评论
                 *      无参数选择   默认报表
                 */
                if (hasParamInfo){

                }else{

                }
                break;

            case R.id.imgThree :
                PLog.e("默认报表被点击");
                break;
        }
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
            webView.loadUrl(resdata.getShowUrl());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshTableEvent event){
        getPresenter().getTableUrl(event.getParamArr(),tableBean.getRes_id());
    }
}
