package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ReportTrashListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public interface ReportOfTrashView extends MvpView {
    void queryReportSuccess(ReportTrashListBean data);

    void restoreSuccess();

    void deleteSuccess();
}