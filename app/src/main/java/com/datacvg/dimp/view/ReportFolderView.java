package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public interface ReportFolderView extends MvpView {
    /**
     * 获取管理画布列表成功
     * @param data
     */
    void getReportSuccess(ReportListBean data);
}
