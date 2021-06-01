package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.IndexChartBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public interface BoardPagerView extends MvpView {
    /**
     * 获取指标信息成功
     * @param indexPosition
     */
    void getIndexPositionSuccess(List<DimensionPositionBean.IndexPositionBean> indexPosition);

    /**
     * 获取图表信息成功
     * @param data
     */
    void getChartSuccess(IndexChartBean data);

    /**
     * 维度信息成功
     * @param selectDimension
     */
    void getDimensionSuccess(List<DimensionBean> selectDimension);

    /**
     * 其他维度信息获取成功
     * @param selectOtherDimension
     * @param tag
     */
    void getOtherDimensionSuccess(List<DimensionBean> selectOtherDimension, String tag);
}
