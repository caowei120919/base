package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 指标实体
 */
@Keep
public class DimensionPositionBean implements Serializable {

    /**
     * existIndexthreshold : true
     * chart_type : line_chart
     * size_y : 2
     * size_x : 2
     * id : IBI-ex-cost_MONTH
     * analysis_dimension : user_org
     * existDescription : true
     */

    private String existIndexthreshold;
    private String chart_type;
    private int size_y;
    private int size_x;
    private String id;
    private String analysis_dimension;
    private String existDescription;
    private ChartBean chartBean ;

    public ChartBean getChartBean() {
        return chartBean;
    }

    public void setChartBean(ChartBean chartBean) {
        this.chartBean = chartBean;
    }

    public String getExistIndexthreshold() {
        return existIndexthreshold;
    }

    public void setExistIndexthreshold(String existIndexthreshold) {
        this.existIndexthreshold = existIndexthreshold;
    }

    public String getChart_type() {
        return chart_type;
    }

    public void setChart_type(String chart_type) {
        this.chart_type = chart_type;
    }

    public int getSize_y() {
        return size_y;
    }

    public void setSize_y(int size_y) {
        this.size_y = size_y;
    }

    public int getSize_x() {
        return size_x;
    }

    public void setSize_x(int size_x) {
        this.size_x = size_x;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnalysis_dimension() {
        return analysis_dimension;
    }

    public void setAnalysis_dimension(String analysis_dimension) {
        this.analysis_dimension = analysis_dimension;
    }

    public String getExistDescription() {
        return existDescription;
    }

    public void setExistDescription(String existDescription) {
        this.existDescription = existDescription;
    }
}
