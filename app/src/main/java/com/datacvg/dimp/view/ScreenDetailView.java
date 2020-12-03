package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ScreenDetailBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :
 */
public interface ScreenDetailView extends MvpView {
    /**
     * 获取大屏详情成功
     * @param bean
     */
    void getScreenDetailSuccess(ScreenDetailBean bean);

    /**
     * 大屏消息发松失败
     */
    void forScreenFailed(String scIndexStatus);

    /**
     * 大屏消息成功
     * @param scIndexStatus
     */
    void forScreenSuccess(String scIndexStatus);
}
