package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public interface ReportOfMineView extends MvpView {
    /**
     * 获取我的报告成功
     * @param reportBeans
     */
    void getReportOfMineSuccess(ReportListBean reportBeans);
}
