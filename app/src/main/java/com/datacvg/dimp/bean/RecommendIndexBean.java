package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-04-20
 * @Description : 指标
 */

@Keep
public class RecommendIndexBean {

    private List<IndexDetailListBean> indexChartRelation;

    public List<IndexDetailListBean> getIndexChartRelation() {
        return indexChartRelation;
    }

    public void setIndexChartRelation(List<IndexDetailListBean> indexChartRelation) {
        this.indexChartRelation = indexChartRelation;
    }
}
