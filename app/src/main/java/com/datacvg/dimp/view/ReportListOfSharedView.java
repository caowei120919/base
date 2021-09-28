package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public interface ReportListOfSharedView extends MvpView {
    /**
     * 获取
     * @param data
     */
    void getReportOfShareSuccess(ReportListBean data);
}
