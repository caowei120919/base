package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
@Keep
public class IndexTreeNeedBean implements Serializable {
    /**
     * orgName : 数聚股份
     * pValue : GOODS
     * fuDimension : 118306192070461277956
     * type : 4
     * fuValue : region
     * pName : 产品或服务
     * orgValue : DATACVG
     * analysisDimension : user_org
     * indexId : IBI-ex-cost_MONTH
     * pDimension : 118341583624371459776
     * fuName : 所有地区
     * timeVal : 202009
     * orgDimension : 14860367656855969470
     * state :
     */

    private String orgName;
    private String pValue;
    private String fuDimension;
    private String type;
    private String fuValue;
    private String pName;
    private String orgValue;
    private String analysisDimension;
    private String indexId;
    private String pDimension;
    private String fuName;
    private String timeVal;
    private String orgDimension;
    private String state;
    private String dimension_clName ;
    private String dimension_flName ;

    public String getDimension_clName() {
        return dimension_clName;
    }

    public void setDimension_clName(String dimension_clName) {
        this.dimension_clName = dimension_clName;
    }

    public String getDimension_flName() {
        return dimension_flName;
    }

    public void setDimension_flName(String dimension_flName) {
        this.dimension_flName = dimension_flName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getFuDimension() {
        return fuDimension;
    }

    public void setFuDimension(String fuDimension) {
        this.fuDimension = fuDimension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFuValue() {
        return fuValue;
    }

    public void setFuValue(String fuValue) {
        this.fuValue = fuValue;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getOrgValue() {
        return orgValue;
    }

    public void setOrgValue(String orgValue) {
        this.orgValue = orgValue;
    }

    public String getAnalysisDimension() {
        return analysisDimension;
    }

    public void setAnalysisDimension(String analysisDimension) {
        this.analysisDimension = analysisDimension;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getpDimension() {
        return pDimension;
    }

    public void setpDimension(String pDimension) {
        this.pDimension = pDimension;
    }

    public String getFuName() {
        return fuName;
    }

    public void setFuName(String fuName) {
        this.fuName = fuName;
    }

    public String getTimeVal() {
        return timeVal;
    }

    public void setTimeVal(String timeVal) {
        this.timeVal = timeVal;
    }

    public String getOrgDimension() {
        return orgDimension;
    }

    public void setOrgDimension(String orgDimension) {
        this.orgDimension = orgDimension;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
