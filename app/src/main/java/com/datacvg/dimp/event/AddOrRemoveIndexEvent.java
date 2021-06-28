package com.datacvg.dimp.event;

import com.datacvg.dimp.bean.IndexChartBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class AddOrRemoveIndexEvent {
    private IndexChartBean bean ;

    public IndexChartBean getBean() {
        return bean;
    }

    public void setBean(IndexChartBean bean) {
        this.bean = bean;
    }

    public AddOrRemoveIndexEvent(IndexChartBean bean) {
        this.bean = bean ;
    }
}
