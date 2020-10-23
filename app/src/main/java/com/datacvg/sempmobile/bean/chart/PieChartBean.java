package com.datacvg.sempmobile.bean.chart;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :   饼图
 */
@Keep
public class PieChartBean {

    /**
     * value : 42
     * name : 大数据事业部
     */

    private float value;
    private String name;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
