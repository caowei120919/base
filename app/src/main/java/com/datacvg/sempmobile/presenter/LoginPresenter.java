package com.datacvg.sempmobile.presenter;

import android.text.TextUtils;
import com.datacvg.sempmobile.BuildConfig;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.config.LoginApi;
import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.baseandroid.utils.StringUtils;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.ServiceBean;
import com.datacvg.sempmobile.bean.UserLoginBean;
import com.datacvg.sempmobile.view.LoginView;

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
                        .getString(R.string.Enter_your_enterprise_id_or_configure_the_semf_service_address));
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
        String url = String.format(Constants.GET_SERVER_URL
                ,(TextUtils.isEmpty(companyCode) ? "" : companyCode) + "_");
        loginApi.getServiceUrl(url)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<ServiceBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(ServiceBean serviceBean) {
                        if(serviceBean != null && serviceBean.getAndroidresdata() != null){
                            ServiceBean.AndroidresdataBean bean = serviceBean.getAndroidresdata();

                            Constants.BASE_FIS_URL = bean.getFisServer() ;
                            Constants.BASE_MOBILE_URL = bean.getHttpServer() ;
                            Constants.BASE_UPLOAD_URL = bean.getUpdateURL() ;

                            RetrofitUrlManager.getInstance().setGlobalDomain(Constants.BASE_MOBILE_URL);
                            RetrofitUrlManager.getInstance().putDomain("upload_apk",Constants.BASE_UPLOAD_URL);
                            RetrofitUrlManager.getInstance().putDomain("fis_api",Constants.BASE_FIS_URL);

                            if(bean.getAppVersion().compareTo(BuildConfig.VERSION_NAME) > 0){
                                getView().onUpdateVersion(bean.getUpdateURL());
                            }else{
                                login(userName,password);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


    public void login(String userName, String password) {
        Map<String,String> params = new HashMap<>();
        params.put("loginname",userName);
        params.put("password",password);
        mobileApi.login(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<UserLoginBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<UserLoginBean> baseBean) {
                        Constants.token = baseBean.getUser_token();
                        getView().loginSuccess(baseBean);
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
}
