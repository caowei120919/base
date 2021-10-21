package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public interface ReportListOfMineView extends MvpView {
    /**
     * 获取我的列表成功
     * @param data
     */
    void getReportOfMineSuccess(ReportListBean data);

    /**
     * 报告资源下载完成
     * @param bean
     */
    void getReportSourceSuccess(String bean);

    /**
     * 删除成功
     */
    void deleteSuccess();
}
