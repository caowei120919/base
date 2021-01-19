package com.datacvg.dimp.presenter;

import android.text.TextUtils;
import com.datacvg.dimp.BuildConfig;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.LoginApi;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StringUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ServiceBean;
import com.datacvg.dimp.bean.UserLoginBean;
import com.datacvg.dimp.view.LoginView;
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

                            if(TextUtils.isEmpty(bean.getFisServer())){
                                Constants.BASE_FIS_URL = bean.getHttpServer() + "/" ;
                            }else{
                                Constants.BASE_FIS_URL = bean.getFisServer() + "/";
                            }
                            PLog.e(Constants.BASE_FIS_URL);
                            Constants.BASE_MOBILE_URL = bean.getHttpServer() ;
                            Constants.BASE_DDB_URL = Constants.BASE_MOBILE_URL.replace("mobile","");
                            Constants.BASE_UPLOAD_URL = bean.getUpdateURL() ;

                            RetrofitUrlManager.getInstance().setRun(true);
                            RetrofitUrlManager.getInstance()
                                    .setGlobalDomain(Constants.BASE_MOBILE_URL);
                            RetrofitUrlManager.getInstance()
                                    .putDomain("upload_apk",Constants.BASE_UPLOAD_URL);
                            RetrofitUrlManager.getInstance()
                                    .putDomain("fis_api",Constants.BASE_FIS_URL);
                            RetrofitUrlManager.getInstance()
                                    .putDomain("local_api",Constants.BASE_LOCAL_URL);
                            RetrofitUrlManager.getInstance()
                                    .putDomain("upload_file","http://dimp.dev.datacvg.com/api/file");
                            RetrofitUrlManager.getInstance()
                                    .putDomain("ddb_api",Constants.BASE_DDB_URL);

                            if(bean.getAppVersion().compareTo(BuildConfig.VERSION_NAME) > 0){
                                getView().onUpdateVersion(bean.getUpdateURL());
                            }else{
                                PLog.e("upload_file  ===== > " + RetrofitUrlManager.getInstance().fetchDomain("upload_file"));
                                login(userName,password);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }


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
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
