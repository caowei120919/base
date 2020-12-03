package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.bean.UserLoginBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-24
 * @Description :
 */
public interface LoginView extends MvpView {
    /**
     * 应用更新
     * @param updateStandardURL
     */
    void onUpdateVersion(String updateStandardURL);

    /**
     * 登录成功
     * @param baseBean
     */
    void loginSuccess(BaseBean<UserLoginBean> baseBean);
}
