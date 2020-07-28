package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.LoginApi;
import com.datacvg.sempmobile.view.SplashView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public class SplashPresenter extends BasePresenter<SplashView> {
    LoginApi api ;

    @Inject
    public SplashPresenter(LoginApi loginApi){
        api = loginApi;
    }
}
