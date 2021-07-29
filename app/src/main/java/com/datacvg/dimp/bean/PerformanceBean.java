package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-03-10
 * @Description :
 */
@Keep
public class PerformanceBean {
    private List<DimensionPositionBean.IndexPositionBean> indexPositionForBudget;

    public List<DimensionPositionBean.IndexPositionBean> getIndexPositionForBudget() {
        return indexPositionForBudget;
    }

    public void setIndexPositionForBudget(List<DimensionPositionBean.IndexPositionBean> indexPositionForBudget) {
        this.indexPositionForBudget = indexPositionForBudget;
    }
}
