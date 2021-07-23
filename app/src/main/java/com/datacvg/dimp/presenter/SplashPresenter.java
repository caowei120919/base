package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.config.UploadApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.TimeValueBean;
import com.datacvg.dimp.view.SplashView;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public class SplashPresenter extends BasePresenter<SplashView> {
    UploadApi uploadApi ;
    MobileApi mobileApi ;

    @Inject
    public SplashPresenter(MobileApi mobileApi,UploadApi uploadApi){
        this.mobileApi = mobileApi ;
        this.uploadApi = uploadApi ;
    }

    /**
     * 登录
     * @param userName
     * @param password
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

}
