package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-13
 * @Description :
 */
@Keep
public class SearchIndexBean implements Serializable {
    private List<IndexChartBean> indexChartBeans = new ArrayList<>() ;

    public List<IndexChartBean> getIndexChartBeans() {
        return indexChartBeans;
    }

    public void setIndexChartBeans(List<IndexChartBean> indexChartBeans) {
        this.indexChartBeans = indexChartBeans;
    }
}
