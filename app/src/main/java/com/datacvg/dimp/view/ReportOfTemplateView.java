package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public interface ReportOfTemplateView extends MvpView {
    /**
     * 报告获取成功
     * @param data
     */
    void getReportOfTemplateSuccess(ReportListBean data);

    void uploadSuccess();

    void getReportSourceSuccess(String bean);
}
