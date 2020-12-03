package com.datacvg.dimp.bean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description :
 */
public class TestBean {
    private void drawData(ChartBean chartBean){
        String max = getMax(chartBean);
        String min = getMin(chartBean);
    }

    private String getMax(ChartBean chartBean) {
        Double max = Double.valueOf(chartBean.getDefault_value());
        if(chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0){
            for (ChartBean.ThresholdArrBean bean :chartBean.getThresholdArr()) {
                max = Math.max(max,Double.valueOf(bean.getThreshold_value()));
            }
        }
        return String.valueOf(max);
    }

    private String getMin(ChartBean chartBean) {
        Double min = Double.valueOf(chartBean.getDefault_value());
        if(chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0){
            for (ChartBean.ThresholdArrBean bean :chartBean.getThresholdArr()) {
                min = Math.min(min,Double.valueOf(bean.getThreshold_value()));
            }
        }
        return String.valueOf(min);
    }
}
