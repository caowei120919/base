package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.IndexChartBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public interface BudgetView extends MvpView {
    /**
     * 绩效数据获取成功
     * @param indexPositionForBudget
     */
    void getBudgetSuccess(List<DimensionPositionBean.IndexPositionBean> indexPositionForBudget);

    /**
     * 获取图表详情成功
     * @param indexChartBean
     */
    void getChartSuccess(IndexChartBean indexChartBean);
}
