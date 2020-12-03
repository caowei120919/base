package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

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
