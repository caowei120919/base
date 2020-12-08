package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.DimensionPositionListBean;
import com.datacvg.dimp.bean.OtherDimensionBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface DigitalView extends MvpView {
    /**
     * 获取维度成功
     * @param dimensions
     */
    void getDimensionSuccess(DimensionListBean dimensions);

    /**
     * 获取指标列表成功
     * @param dimensionPositionBeans
     */
    void getDimensionPositionSuccess(DimensionPositionListBean dimensionPositionBeans);

    /**
     * 获取数据图表成功
     * @param chartBeans
     */
    void getChartSuccess(ChartListBean chartBeans);

    /**
     * 获取其他维度成功
     * @param type
     * @param data
     */
    void getOtherDimensionSuccess(String type, DimensionListBean data);
}
