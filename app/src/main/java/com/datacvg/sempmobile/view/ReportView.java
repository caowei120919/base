package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface ReportView extends MvpView {
    /**
     * 获取报告列表成功
     * @param data
     */
    void getReportSuccess(ReportListBean data);
}
