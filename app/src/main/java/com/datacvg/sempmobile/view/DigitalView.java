package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.DimensionBean;
import com.datacvg.sempmobile.bean.DimensionListBean;
import com.datacvg.sempmobile.bean.DimensionPositionListBean;
import com.datacvg.sempmobile.bean.OtherDimensionBean;

import java.util.List;

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
     * 获取其他维度成功
     * @param dimensions
     */
    void getOtherDimensionSuccess(OtherDimensionBean dimensions);

    /**
     * 获取指标列表成功
     * @param dimensionPositionBeans
     */
    void getDimensionPositionSuccess(DimensionPositionListBean dimensionPositionBeans);
}
