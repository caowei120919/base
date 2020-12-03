package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.LoginApi;
import com.datacvg.dimp.view.SplashView;

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
