package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-20
 * @Description :
 */
public interface SearchReportView extends MvpView {

    /**
     * 查询的所有报告获取成功
     * @param data
     */
    void getReportSuccess(ReportListBean data);
}
