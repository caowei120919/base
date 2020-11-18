package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.UserLoginBean;
import com.datacvg.sempmobile.view.LoginWebView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-18
 * @Description :
 */
public class LoginWebPresenter extends BasePresenter<LoginWebView> {

    MobileApi api ;

    @Inject
    public LoginWebPresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 扫码登录
     * @param uuid
     * @param token
     */
    public void loginWeb(String uuid, String token) {
        Map<String,String> params = new HashMap<>();
        params.put("deviceId",uuid);
        params.put("token",token);
        api.webLogin(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<String>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        getView().loginSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
