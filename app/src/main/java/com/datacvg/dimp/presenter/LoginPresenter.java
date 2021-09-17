package com.datacvg.dimp.presenter;

import android.text.TextUtils;
import com.datacvg.dimp.BuildConfig;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.LoginApi;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.download.DownloadException;
import com.datacvg.dimp.baseandroid.download.DownloadManager;
import com.datacvg.dimp.baseandroid.download.SimpleDownLoadCallBack;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StringUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.CheckVersionBean;
import com.datacvg.dimp.bean.TimeValueBean;
import com.datacvg.dimp.bean.UserLoginBean;
import com.datacvg.dimp.view.LoginView;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-24
 * @Description :
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    LoginApi loginApi;
    MobileApi mobileApi ;

    @Inject
    public LoginPresenter(LoginApi api,MobileApi mobileApi) {
        this.mobileApi = mobileApi ;
        this.loginApi = api;
    }

    /**
     * 常规非VPN登录
     * @param companyCode   企业标识码
     * @param userName      用户账号
     * @param password      用户密码
     */
    public void checkUrlOrVersion(String companyCode, String userName, String password) {
        if(TextUtils.isEmpty(companyCode)){
            if(StringUtils.equals(Constants.BASE_URL,Constants.DATA_CVG_BASE_URL)){
                ToastUtils.showLongToast(AndroidUtils.getContext().getResources()
                        .getString(R.string.please_enter_merchant));
                return;
            }
        }else{
            getLicenseCode(companyCode);
            getServiceUrl(companyCode,userName,password);
        }
    }

    /**
     * 通过企业验证码获取服务器地址
     * @param companyCode   企业码
     * @param userName      用户名
     * @param password      密码
     */
    private void getServiceUrl(String companyCode, String userName, String password) {
        String url = "";
        switch (companyCode){
            case Constants.DEV_CODE :
                url = Constants.BASE_DEV_MERCHANT;
                break;

            case Constants.TEST_CODE :
                url = Constants.BASE_TEST_MERCHANT;
                break;

            default:
                url = String.format(Constants.BASE_MERCHANT,companyCode);
                break;
        }
        Constants.BASE_MOBILE_URL = url ;
        RetrofitUrlManager.getInstance().setRun(true);
        RetrofitUrlManager.getInstance()
                .setGlobalDomain(url);
        login(userName,password);
    }


    /**
     * 登录
     * @param userName 登录名
     * @param password 登录密码
     */
    public void login(String userName, String password) {
        Map<String,String> params = new HashMap<>();
        params.put("login_name",userName);
        params.put("user_password",password);
        params.put("deviceToken","");
        params.put("deviceType","android");
        mobileApi.login(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if(RxObserver.checkJsonCode(baseBean)){
                            Constants.token = baseBean.getUser_token();
                            getTimeValue() ;
                            getView().loginSuccess(baseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                        Constants.BASE_FIS_URL = "" ;
                        Constants.BASE_MOBILE_URL = "" ;
                        Constants.BASE_UPLOAD_URL = "" ;
                        Constants.token = "" ;
                        RetrofitUrlManager.getInstance()
                                .setGlobalDomain(Constants.BASE_URL);
                        RetrofitUrlManager.getInstance().setRun(false);
                    }
                });
    }

    /**
     * 获取时间信息
     */
    private void getTimeValue() {
        mobileApi.getTimeVal()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TimeValueBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TimeValueBean> timeValueBeanBaseBean) {
                        PreferencesHelper.put(Constants.USER_DEFAULT_MONTH
                                ,timeValueBeanBaseBean.getData().getDefaultTime().getMonth());
                        PreferencesHelper.put(Constants.USER_DEFAULT_YEAR
                                ,timeValueBeanBaseBean.getData().getDefaultTime().getYear());
                        PreferencesHelper.put(Constants.USER_DEFAULT_DAY
                                ,timeValueBeanBaseBean.getData().getDefaultTime().getDay());
                        getView().getTimeValueSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 获取河狸license
     * @param companyCode 企业标识码
     */
    private void getLicenseCode(String companyCode) {
        if(!BuildConfig.APP_STAND || TextUtils.isEmpty(companyCode)){
            return;
        }
        String customUrl = String.format(Constants.CUSTOM_CODE_URL,companyCode);
        loginApi.getCustomLicense(customUrl)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<String>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void checkVersion() {
        Map params = new HashMap();
        params.put("device","android");
        loginApi.checkVersion(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<CheckVersionBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(CheckVersionBean bean) {
                        PLog.e(bean.getDesc());
                        getView().checkVersionSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     *
     * @param download
     * @param mDownLoadApkFolder
     * @param mDownLoadApkFileName
     */
    public void requestDownload(String download, String mDownLoadApkFolder, String mDownLoadApkFileName) {
        DownloadManager.getInstance().init(AndroidUtils.getContext());
        DownloadManager.getInstance()
                .download(download, new File(mDownLoadApkFolder), mDownLoadApkFileName
                        , false, new SimpleDownLoadCallBack() {

                            @Override
                            public void onProgress(long finished, long total, int progress) {
                                PLog.e(progress + "");
                            }

                            @Override
                            public void onCompleted(File downloadfile) {
                                if (getView() != null) {
                                    getView().downloadCompleted(downloadfile.getName());
                                }
                            }

                            @Override
                            public void onFailed(DownloadException e) {
                                PLog.e(e.getErrorMessage());
                            }
                        });
    }
}
