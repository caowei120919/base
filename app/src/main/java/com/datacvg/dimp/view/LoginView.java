package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.bean.CheckVersionBean;
import com.datacvg.dimp.bean.UserLoginBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-24
 * @Description :
 */
public interface LoginView extends MvpView {
    /**
     * 登录成功
     * @param baseBean
     */
    void loginSuccess(BaseBean baseBean);

    /**
     * 检查更新
     * @param bean
     */
    void checkVersionSuccess(CheckVersionBean bean);

    /**
     * 文件下载完成
     * @param name
     */
    void downloadCompleted(String name);

    /**
     * 获取时间权限信息成功
     */
    void getTimeValueSuccess();
}
