package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public interface ReportOfSharedView extends MvpView {
    /**
     * 报告列表获取成功
     * @param data
     */
    void getReportOfShareSuccess(ReportListBean data);

    /**
     * 缩略图上传成功
     */
    void uploadSuccess();

    void getReportSourceSuccess(String bean);

    /**
     * 删除成功
     */
    void deleteSuccess();
}
