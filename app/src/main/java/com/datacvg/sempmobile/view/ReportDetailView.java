package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.ReportParamsBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-12
 * @Description :
 */
public interface ReportDetailView extends MvpView {
    /**
     * 获取管理画布参数成功
     * @param resdata
     */
    void getParamsSuccess(ReportParamsBean resdata);
}
