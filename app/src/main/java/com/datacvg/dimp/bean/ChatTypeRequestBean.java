package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 图标请求实体
 */
@Keep
public class ChatTypeRequestBean implements Serializable {

    /**
     * fuValue : region
     * orgValue : DATACVG
     * pValue : GOODS
     * orgDimension : 14860367656855969470
     * fuDimension : 118306192070461277956
     * pDimension : 118341583624371459776
     * lang : zh
     * timeValue : 202004
     * chartType : [{"indexId":"IBI-rent","dataType":"text","analysisDim":"TIME"}]
     */

    private String fuValue;
    private String orgValue;
    private String pValue;
    private String orgDimension;
    private String fuDimension;
    private String pDimension;
    private String lang;
    private String timeValue;
    private List<ChartTypeBean> chartType;
    private List<ChartBean> chartBeans ;

    public List<ChartBean> getChartBeans() {
        return chartBeans;
    }

    public void setChartBeans(List<ChartBean> chartBeans) {
        this.chartBeans = chartBeans;
    }

    public String getFuValue() {
        return fuValue;
    }

    public void setFuValue(String fuValue) {
        this.fuValue = fuValue;
    }

    public String getOrgValue() {
        return orgValue;
    }

    public void setOrgValue(String orgValue) {
        this.orgValue = orgValue;
    }

    public String getPValue() {
        return pValue;
    }

    public void setPValue(String pValue) {
        this.pValue = pValue;
    }

    public String getOrgDimension() {
        return orgDimension;
    }

    public void setOrgDimension(String orgDimension) {
        this.orgDimension = orgDimension;
    }

    public String getFuDimension() {
        return fuDimension;
    }

    public void setFuDimension(String fuDimension) {
        this.fuDimension = fuDimension;
    }

    public String getPDimension() {
        return pDimension;
    }

    public void setPDimension(String pDimension) {
        this.pDimension = pDimension;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public List<ChartTypeBean> getChartType() {
        return chartType;
    }

    public void setChartType(List<ChartTypeBean> chartType) {
        this.chartType = chartType;
    }

    public static class ChartTypeBean implements Serializable{
        /**
         * indexId : IBI-rent
         * dataType : text
         * analysisDim : TIME
         */

        private String indexId;
        private String dataType;
        private String analysisDim;
        private String defaultAnalysis ;

        public String getDefaultAnalysis() {
            return defaultAnalysis;
        }

        public void setDefaultAnalysis(String defaultAnalysis) {
            this.defaultAnalysis = defaultAnalysis;
        }

        public String getIndexId() {
            return indexId;
        }

        public void setIndexId(String indexId) {
            this.indexId = indexId;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getAnalysisDim() {
            return analysisDim;
        }

        public void setAnalysisDim(String analysisDim) {
            this.analysisDim = analysisDim;
        }
    }
}
