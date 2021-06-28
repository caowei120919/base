package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.IndexChartBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public interface ChartDetailView extends MvpView {

    /**
     * 图表数据获取成功
     * @param bean
     */
    void getChartSuccess(IndexChartBean bean);
}
