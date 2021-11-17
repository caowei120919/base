package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.UserJobsListBean;

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

    /**
     * 获取消息成功
     * @param resdata
     */
    void getMessageSuccess(MessageBean resdata);

    /**
     * 岗位切换成功
     */
    void switchJobSuccess();
}
