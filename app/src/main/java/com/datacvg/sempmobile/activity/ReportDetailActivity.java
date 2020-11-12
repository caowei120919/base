package com.datacvg.sempmobile.activity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.datacvg.sempmobile.baseandroid.utils.LanguageUtils;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.ReportBean;
import com.datacvg.sempmobile.bean.ReportParamsBean;
import com.datacvg.sempmobile.presenter.ReportDetailPresenter;
import com.datacvg.sempmobile.view.ReportDetailView;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 报告，报表统一展示的页面
 */
public class ReportDetailActivity extends BaseActivity<ReportDetailView, ReportDetailPresenter>
        implements ReportDetailView {
    private final String TIME_OF_POINT = "point" ;
    private final String TIME_OF_PERIOD = "period" ;

    @BindView(R.id.webView)
    WebView webView ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.img_other)
    ImageView imgOther ;

    private ReportBean bean ;
    private String reportId = "" ;
    private ReportParamsBean.ParamsResultBean paramsResultBean ;
    private String serviceUrl = Constants.BASE_FIS_URL;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_share));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_filter));
        initWebView();
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        bean = (ReportBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(bean == null){
            finish();
        }
        switch (bean.getReport_type()){
            case Constants.REPORT_MINE :
                tvTitle.setText(LanguageUtils.isZh(mContext)
                        ? bean.getModel_clname() : bean.getModel_flname());
                reportId = bean.getModel_id() ;
                break;

            default:
                tvTitle.setText(LanguageUtils.isZh(mContext)
                        ? bean.getShare_clname() : bean.getShare_flname());
                reportId = bean.getShare_id() ;
                break;
        }
        serviceUrl = serviceUrl + "/dataexporler" ;
        try {
            serviceUrl = URLEncoder.encode(serviceUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            serviceUrl = URLEncoder.encode(serviceUrl);
        }
        getReportParameters();
    }

    /**
     * 获取报表相关参数
     */
    private void getReportParameters() {
        getPresenter().getReportParameters(reportId) ;
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
    }

    @OnClick({R.id.img_left,R.id.img_other,R.id.img_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.img_other :
                    if(paramsResultBean == null){
                        ToastUtils.showLongToast(resources
                                .getString(R.string.the_current_report_has_no_optional_parameters));
                    }else{
                        checkParams();
                    }
                break;
        }
    }

    /**
     * 校验参数
     */
    private void checkParams() {
        /**
         * 时间点
         */
        if(paramsResultBean.getTimeFormat().equals(TIME_OF_POINT)){

        }else{

        }
    }

    /**
     * 获取报告参数成功
     * @param resdata
     */
    @Override
    public void getParamsSuccess(ReportParamsBean resdata) {
        String paramsString = resdata.getParams()
                .replace("[","").replace("]","");
        paramsResultBean
                = new Gson().fromJson(paramsString, ReportParamsBean.ParamsResultBean.class);
//        if (paramsResultBean == null){
            String url = "file:///android_asset/mobile.html?lang=" + LanguageUtils.getLanguage(mContext)
                    + "#/" + serviceUrl + "/"  + reportId + "/" +Constants.token + "?themeName=dap";
            webView.loadUrl(url);
            PLog.e(url);
//        }
    }
}
