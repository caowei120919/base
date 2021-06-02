package com.datacvg.dimp.bean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-04-20
 * @Description :
 */
public class IndexDetailListBean {
    private String name;
    private List<IndexChartBean> detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IndexChartBean> getDetail() {
        return detail;
    }

    public void setDetail(List<IndexChartBean> detail) {
        this.detail = detail;
    }
}
