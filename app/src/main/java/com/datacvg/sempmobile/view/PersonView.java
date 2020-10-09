package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.UserJobsListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface PersonView extends MvpView {

    /**
     * 成功退出登录
     */
    void loginOutSuccess();

    /**
     * 用户职位获取成功
     * @param resdata
     */
    void getUseJobsSuccess(UserJobsListBean resdata);
}
