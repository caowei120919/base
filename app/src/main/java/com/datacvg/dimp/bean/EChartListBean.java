package com.datacvg.dimp.bean;

import androidx.annotation.Keep;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-03-12
 * @Description :
 */
@Keep
public class EChartListBean {

    public List<IndexChartBean> getIndexChart() {
        return indexChart;
    }

    public void setIndexChart(List<IndexChartBean> indexChart) {
        this.indexChart = indexChart;
    }

    private List<IndexChartBean> indexChart;
}
