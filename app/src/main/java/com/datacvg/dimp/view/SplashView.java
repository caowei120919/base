package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.bean.CheckVersionBean;
import com.datacvg.dimp.bean.UserLoginBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public interface SplashView extends MvpView {

    /**
     * 登录成功
     * @param baseBean
     */
    void loginSuccess(BaseBean<UserLoginBean> baseBean);

}
