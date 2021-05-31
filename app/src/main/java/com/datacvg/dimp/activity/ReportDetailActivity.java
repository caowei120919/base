package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.baseandroid.widget.dialog.BaseWindowDialog;
import com.datacvg.dimp.baseandroid.widget.dialog.DialogViewHolder;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportParamsBean;
import com.datacvg.dimp.presenter.ReportDetailPresenter;
import com.datacvg.dimp.view.ReportDetailView;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

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

    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    private BaseWindowDialog selectParamsView ;

    private ReportBean bean ;
    private String reportId = "" ;
    private ReportParamsBean.ParamsResultBean paramsResultBean ;
    private String serviceUrl = Constants.BASE_URL;
    /**
     * 时间点的时间参数
     */
    private String reportTime ;
    /**
     * 时间段的开始时间参数
     */
    private String beginTime ;
    /**
     * 时间段的结束时间参数
     */
    private String endTime ;

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
        serviceUrl = serviceUrl + "api/dataengine/dataexporler" ;
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
//                        dealWithParams();
                        initSelectParamsView();
                    }
                break;
        }
    }

    /**
     * 获取报告参数成功
     * @param resdata
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getParamsSuccess(ReportParamsBean resdata) {
        String paramsString = resdata.getParams()
                .replace("[","").replace("]","");
        paramsResultBean
                = new Gson().fromJson(paramsString, ReportParamsBean.ParamsResultBean.class);
        if (paramsResultBean == null){
            loadWebUrl();
        }else{
            dealWithParams();
        }
    }

    /**
     * 参数处理
     *      初始化报表相关参数，配置参数选择控件相关参数
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dealWithParams() {
        switch (paramsResultBean.getUnit()){
            /**
             * 日
             */
            case "day" :
                    initCustomPickView(true,true,true);
                    if(paramsResultBean.getTimeShow().equals(TIME_OF_POINT)){
                        if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                            reportTime = TimeUtils.getNewStrDateForStr(TimeUtils
                                            .date2Str(TimeUtils.addDay(new Date()
                                    ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                    ,TimeUtils.FORMAT_YMD_CN
                                    ,paramsResultBean.getTimeFormat().replace("YYYY"
                                            ,"yyyy")
                                            .replace("DD","dd"));
                        }
                    }else{
                        if(!TextUtils.isEmpty(paramsResultBean.getStartTime())){
                            beginTime = TimeUtils.getNewStrDateForStr(TimeUtils.date2Str(TimeUtils.addDay(new Date()
                                    ,- Integer.valueOf(paramsResultBean.getStartTime()))),TimeUtils.FORMAT_YMD_CN
                                    ,paramsResultBean.getTimeFormat().replace("YYYY"
                                            ,"yyyy").replace("DD","dd"));
                        }
                        if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                            endTime = TimeUtils.getNewStrDateForStr(TimeUtils.date2Str(TimeUtils.addDay(new Date()
                                    ,- Integer.valueOf(paramsResultBean.getEndTime()))),TimeUtils.FORMAT_YMD_CN
                                    ,paramsResultBean.getTimeFormat().replace("YYYY"
                                            ,"yyyy").replace("DD","dd"));
                        }
                    }
                break;

            /**
             *
             */
            case "week" :
                initCustomPickView(true,true,true);
                if(paramsResultBean.getTimeShow().equals(TIME_OF_POINT)){
                    if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                        reportTime = TimeUtils.getNewStrDateInWeekForStr(
                                TimeUtils.date2Str(TimeUtils.addWeek(new Date()
                                ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                ,TimeUtils.FORMAT_YMD_CN);
                    }
                }else{
                    if(!TextUtils.isEmpty(paramsResultBean.getStartTime())){
                        beginTime = TimeUtils.getNewStrDateInWeekForStr(TimeUtils
                                        .date2Str(TimeUtils.addWeek(new Date()
                                ,- Integer.valueOf(paramsResultBean.getStartTime())))
                                ,TimeUtils.FORMAT_YMD_CN);
                    }
                    if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                        endTime = TimeUtils.getNewStrDateInWeekForStr(TimeUtils
                                .date2Str(TimeUtils.addWeek(new Date()
                                ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                ,TimeUtils.FORMAT_YMD_CN);
                    }
                }
                break;

            /**
             * 月
             */
            case "month" :
                initCustomPickView(true,true,false);
                if(paramsResultBean.getTimeShow().equals(TIME_OF_POINT)){
                    if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                        reportTime = TimeUtils.getNewStrDateForStr(TimeUtils
                                        .date2Str(TimeUtils.addMonth(new Date()
                                ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                ,TimeUtils.FORMAT_YMD_CN
                                ,TimeUtils.FORMAT_YM);
                    }
                }else{
                    if(!TextUtils.isEmpty(paramsResultBean.getStartTime())){
                        beginTime = TimeUtils.getNewStrDateForStr(TimeUtils
                                        .date2Str(TimeUtils.addMonth(new Date()
                                ,- Integer.valueOf(paramsResultBean.getStartTime())))
                                ,TimeUtils.FORMAT_YMD_CN
                                ,TimeUtils.FORMAT_YM);
                    }
                    if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                        endTime = TimeUtils.getNewStrDateForStr(TimeUtils
                                        .date2Str(TimeUtils.addMonth(new Date()
                                ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                ,TimeUtils.FORMAT_YMD_CN
                                ,TimeUtils.FORMAT_YM);
                    }
                }
                break;

            /**
             * 季度
             */
            case "quarterly" :
                initCustomPickView(true,true,true);
                 if(paramsResultBean.getTimeShow().equals(TIME_OF_POINT)){
                    if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                        reportTime = TimeUtils.getNewStrDateInQuarterForStr(TimeUtils
                                .date2Str(TimeUtils.addQuarter(new Date()
                                ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                ,TimeUtils.FORMAT_YMD_CN);
                    }
                }else{
                    if(!TextUtils.isEmpty(paramsResultBean.getStartTime())){
                        beginTime = TimeUtils.getNewStrDateInQuarterForStr(TimeUtils
                                .date2Str(TimeUtils.addQuarter(new Date()
                                ,- Integer.valueOf(paramsResultBean.getStartTime())))
                                ,TimeUtils.FORMAT_YMD_CN);
                    }
                    if(!TextUtils.isEmpty(paramsResultBean.getEndTime())){
                        endTime = TimeUtils.getNewStrDateInQuarterForStr(TimeUtils
                                .date2Str(TimeUtils.addQuarter(new Date()
                                ,- Integer.valueOf(paramsResultBean.getEndTime())))
                                ,TimeUtils.FORMAT_YMD_CN);
                    }
                }
                break;
        }
        loadWebUrl();
    }


    /**
     * 加载网页url参数整理
     */
    private void loadWebUrl() {
        String url = "" ;
        if(paramsResultBean == null){
             url = "file:///android_asset/mobile.html?lang=" + LanguageUtils.getLanguage(mContext)
                    + "#/" + serviceUrl + "/"  + reportId + "/" +Constants.token + "?themeName=dap";
            webView.loadUrl(url);
            PLog.e(url);
            return;
        }
        switch (paramsResultBean.getTimeShow()){
            case TIME_OF_POINT :
                url = "file:///android_asset/mobile.html?lang=" + LanguageUtils.getLanguage(mContext)
                        + "#/" + serviceUrl + "/"  + reportId + "/" +Constants.token + "?reportTime = "
                        + reportTime +  "?themeName=dap";
                break;

            case TIME_OF_PERIOD :
                url = "file:///android_asset/mobile.html?lang=" + LanguageUtils.getLanguage(mContext)
                        + "#/" + serviceUrl + "/"  + reportId + "/" +Constants.token + "?beginTime="
                        + beginTime + "&endTime="
                        + endTime +  "?themeName=dap";
                break;
        }
        webView.loadUrl(url);
        PLog.e(url);
    }

    /**
     *  初始化选择器
     */
    private void initSelectParamsView(){
        if(selectParamsView != null){
            selectParamsView.showDialog();
        }else{
            selectParamsView = new BaseWindowDialog(mContext, R.layout.alertdialog_dismiss_okcancel) {
                @Override
                public void convert(DialogViewHolder holder) {
                    holder.getConvertView().setBackground(new BitmapDrawable());
                    TextView tvSure = holder.getView(R.id.tv_sure);
                    ImageView imgDismiss = holder.getView(R.id.img_dismiss);
                    TextView tvStartTime = holder.getView(R.id.tv_startTime);
                    TextView tvEndTimeTitle = holder.getView(R.id.tv_endTimeTitle);
                    TextView tvEndTime = holder.getView(R.id.tv_endTime);
                    if(paramsResultBean.getTimeShow().equals(TIME_OF_POINT)){
                        tvEndTimeTitle.setVisibility(View.GONE);
                        tvEndTime.setVisibility(View.GONE);
                    }
                    tvStartTime.setOnClickListener(view -> {
                        if (pvCustomTime != null){
                            pvCustomTime.show();
                            pvCustomTime.getDialogContainerLayout().setTag(tvStartTime);
                        }
                    });
                    tvEndTime.setOnClickListener(view -> {
                        if(pvCustomTime != null){
                            pvCustomTime.show();
                            pvCustomTime.getDialogContainerLayout().setTag(tvEndTime);
                        }
                    });
                    tvSure.setOnClickListener(view -> {
                        if (paramsResultBean != null){
                            switch (paramsResultBean.getTimeShow()){
                                case TIME_OF_POINT :
                                        reportTime = tvStartTime.getText().toString() ;
                                    break;

                                default:
                                        beginTime = tvStartTime.getText().toString() ;
                                        endTime = tvEndTime.getText().toString() ;
                                    break;
                            }
                            loadWebUrl();
                        }
                        if (selectParamsView != null){
                            selectParamsView.cancelDialog();
                        }
                    });
                    imgDismiss.setOnClickListener(view -> {
                        if (selectParamsView != null){
                            selectParamsView.cancelDialog();
                        }
                    });
                }
            }.backgroundLight(0.2)
                    .fromBottomToMiddle()
                    .showDialog()
                    .setWidthAndHeight((int) resources.getDimension(R.dimen.W600)
                            , (int) resources.getDimension(R.dimen.H500))
                    .setCancelAble(true)
                    .setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 初始化时间选择器
     * @param yearVisibility
     * @param monthVisibility
     * @param dayVisibility
     */
    private void initCustomPickView(boolean yearVisibility, boolean monthVisibility
            , boolean dayVisibility) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date());
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2000,0,0);
        endDate.set(selectedDate.get(Calendar.YEAR) + 2, 11
                , 30);
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSelect(Date date, View v) {
                if (paramsResultBean!= null){
                    switch (paramsResultBean.getUnit()){
                        case "day" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.date2Str(date,TimeUtils.FORMAT_YMD_EN));
                            break;

                        case "month" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.date2Str(date,TimeUtils.FORMAT_YM));
                            break;

                        case "week" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.getNewStrDateInWeekForStr(TimeUtils.date2Str(date)
                                            ,TimeUtils.FORMAT_YMD_CN));
                            break;

                        default:
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.getNewStrDateInQuarterForStr(TimeUtils.date2Str(date)
                                            ,TimeUtils.FORMAT_YMD_CN));
                            break;
                    }
                }
            }
        })
                .setType(new boolean[]{yearVisibility, monthVisibility, dayVisibility, false, false, false})
                .setLayoutRes(R.layout.pickerview_report_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(view -> {
                            pvCustomTime.returnData();
                            pvCustomTime.dismiss();
                        });

                        ivCancel.setOnClickListener(view -> {
                            pvCustomTime.dismiss();
                        });
                    }
                })
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("")
                .setOutSideCancelable(false)
                .isCyclic(true)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .isDialog(true)
                .build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
