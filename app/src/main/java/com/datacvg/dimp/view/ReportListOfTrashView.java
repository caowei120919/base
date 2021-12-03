package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.bean.ReportTrashBean;
import com.datacvg.dimp.bean.ReportTrashListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public interface ReportListOfTrashView extends MvpView {
    /**
     * 报告文件查询成功
     * @param data
     */
    void queryReportSuccess(ReportTrashListBean data);

    void deleteSuccess();

    void restoreSuccess();

    void clearSuccess();
}
