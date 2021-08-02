package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.TableListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public interface ReportListView extends MvpView {

    /**
     * 获取关联报表成功
     * @param resdata
     */
    void getReportsSuccess(TableListBean resdata);
}
