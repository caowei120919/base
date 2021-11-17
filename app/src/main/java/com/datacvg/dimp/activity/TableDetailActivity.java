package com.datacvg.dimp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.dimp.bean.ConstantReportBean;
import com.datacvg.dimp.bean.SetDefaultResBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableParamInfoBean;
import com.datacvg.dimp.event.RefreshTableEvent;
import com.datacvg.dimp.presenter.TableDetailPresenter;
import com.datacvg.dimp.view.TableDetailView;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @BindView(R.id.container)
    RelativeLayout container ;

    private TableBean tableBean ;
    /**
     * 是否有参数选择
     *      默认没有
     */
    private boolean hasParamInfo = false ;
    private boolean isDefaultReport = false ;
    protected AgentWeb mAgentWeb;
    /**
     * 报表参数选择筛选条件拼接
     */
    private String paramArr = "" ;
    private String serviceUrl = Constants.BASE_MOBILE_URL;

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

    /**
     * 初始化web
     */
    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DERECT)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go("");
        serviceUrl = serviceUrl + "api/dataengine/dataexporler" ;
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tableBean = (TableBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(tableBean == null){
            finish();
            return;
        }
        isDefaultReport = Constants.constantReportBean != null
                && Constants.constantReportBean.getResId().equals(tableBean.getRes_id());
        tvTitle.setText(LanguageUtils.isZh(mContext)
                    ? tableBean.getRes_clname() : tableBean.getRes_flname());
        getResParamInfo();
        getPresenter().getTableUrl(paramArr,tableBean.getRes_id());
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
                        Intent commentIntent = new Intent(mContext,TableCommentActivity.class);
                        commentIntent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,tableBean);
                        startActivity(commentIntent);
                    }
                break;

            case R.id.imgTwo :
                /**
                 * 有参数选择  评论
                 *      无参数选择   默认报表
                 */
                if (hasParamInfo){
                    Intent commentIntent = new Intent(mContext,TableCommentActivity.class);
                    commentIntent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,tableBean);
                    startActivity(commentIntent);
                }else{
                    showSetDefaultDialog();
                }
                break;

            case R.id.imgThree :
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
            dialogOKCancel.dismiss();
        });
        dialogOKCancel.show();
    }


    /**
     * 获取报表参数成功
     * @param tableParamInfoListBean
     *         不为空并且有维度或者参数选择，则允许跳转到选择页面
     */
    @Override
    public void getParamInfoSuccess(List<TableParamInfoBean> tableParamInfoListBean) {
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
                    String url = "file:///android_asset/mobile.html?embedUrl=" + Uri.encode(resdata.getShowUrl()) + "&accessToken=" + Uri.encode(resdata.getData().getToken()) + "&reportId=" + Uri.encode(resdata.getData().getReportId());
                    mAgentWeb.getUrlLoader().loadUrl(url);
                    break;

                case "MODEL" :
                    mAgentWeb.getUrlLoader().loadUrl(Constants.BASE_MOBILE_URL + "/dataexporler/mobile.html?" +"semf_lang=" + LanguageUtils.getLanguage(mContext)
                            + "&userpkid=" + PreferencesHelper.get(Constants.USER_PKID,"") + "#/" + Uri.encode(serviceUrl) + "/" + tableBean.getRes_cuid() + "/" + Constants.token + "?"
                            + paramArr + "&themeName=" + ((tableBean.getModel_screen().equals("screen")) ? "dark" : "dap"));
                    break;

                default:
                    mAgentWeb.getUrlLoader().loadUrl(resdata.getShowUrl());
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
}
