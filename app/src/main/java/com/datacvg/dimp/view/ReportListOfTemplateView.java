package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public interface ReportListOfTemplateView extends MvpView {
    /**
     * 报告获取成功
     * @param data
     */
    void getReportOfTemplateSuccess(ReportListBean data);

    /**
     * 删除成功
     */
    void deleteSuccess();

    /**
     * 报告资源获取成功
     * @param bean
     */
    void getReportSourceSuccess(String bean);
}
