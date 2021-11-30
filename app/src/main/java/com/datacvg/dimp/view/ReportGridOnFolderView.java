package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-27
 * @Description :
 */
public interface ReportGridOnFolderView extends MvpView {
    void getReportSuccess(ReportListBean data);

    void getReportSourceSuccess(String bean);

    void deleteSuccess();

    void uploadSuccess();
}
