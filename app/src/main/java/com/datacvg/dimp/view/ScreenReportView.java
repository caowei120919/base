package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ScreenReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-03-24
 * @Description :
 */
public interface ScreenReportView extends MvpView {


    void getReportInScreenSuccess(ScreenReportListBean data);

    void addToScreenSuccess();
}
