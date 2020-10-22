package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.ScreenDetailBean;

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
}
