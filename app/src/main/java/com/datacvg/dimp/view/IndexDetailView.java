package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ChartListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
public interface IndexDetailView extends MvpView {
    /**
     * 获取图表信息成功
     * @param resdata
     */
    void getChartSuccess(ChartListBean resdata);
}
