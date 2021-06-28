package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.DimensionForTimeBean;
import com.datacvg.dimp.bean.IndexChartInfoBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-01
 * @Description :
 */
public interface AddIndexPageView extends MvpView {
    /**
     * 根据时间获取维度成功
     * @param data
     */
    void getIndexSuccess(DimensionForTimeBean data);

    /**
     * 添加页成功
     */
    void addPageSuccess();

    /**
     * 根据维度获取指标
     * @param data
     */
    void getIndexForDimensionSuccess(IndexChartInfoBean data);
}
