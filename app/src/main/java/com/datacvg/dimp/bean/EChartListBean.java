package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-03-12
 * @Description :
 */
@Keep
public class EChartListBean implements Serializable {
    private String pageNo ;
    private String pageName ;
    private List<IndexChartBean> indexChart;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public List<IndexChartBean> getIndexChart() {
        return indexChart;
    }

    public void setIndexChart(List<IndexChartBean> indexChart) {
        this.indexChart = indexChart;
    }
}
