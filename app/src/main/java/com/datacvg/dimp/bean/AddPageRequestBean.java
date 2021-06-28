package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-14
 * @Description :
 */
@Keep
public class AddPageRequestBean {

    /**
     * indexes : ["IBI-ex-cost_MONTH"]
     * timeType : month
     * padNo : 8
     * padName : ssss
     * dimensions : ["14860367656855969470"]
     */

    private List<String> indexes;
    private String timeType;
    private String padNo;
    private String padName;
    private List<String> dimensions;

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getPadNo() {
        return padNo;
    }

    public void setPadNo(String padNo) {
        this.padNo = padNo;
    }

    public String getPadName() {
        return padName;
    }

    public void setPadName(String padName) {
        this.padName = padName;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }
}
