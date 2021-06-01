package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-28
 * @Description :
 */

@Keep
public class PageItemBean implements Comparable<PageItemBean>, Serializable {


    /**
     * page : 14
     * pad_name : 财务
     * dimensions : ["14860367656855969470","118306192070461277956","118341583624371459776"]
     * time_type : month
     */

    private String page;
    private String pad_name;
    private String dimensions;
    private String timeVal ;
    private String time_type;
    private String mOrgDimension ;
    private String mOrgValue ;
    private String mFuDimension ;
    private String mFuValue ;
    private String mPDimension ;
    private String mpValue ;

    public String getTimeVal() {
        return timeVal;
    }

    public void setTimeVal(String timeVal) {
        this.timeVal = timeVal;
    }

    public String getmOrgDimension() {
        return mOrgDimension;
    }

    public void setmOrgDimension(String mOrgDimension) {
        this.mOrgDimension = mOrgDimension;
    }

    public String getmOrgValue() {
        return mOrgValue;
    }

    public void setmOrgValue(String mOrgValue) {
        this.mOrgValue = mOrgValue;
    }

    public String getmFuDimension() {
        return mFuDimension;
    }

    public void setmFuDimension(String mFuDimension) {
        this.mFuDimension = mFuDimension;
    }

    public String getmFuValue() {
        return mFuValue;
    }

    public void setmFuValue(String mFuValue) {
        this.mFuValue = mFuValue;
    }

    public String getmPDimension() {
        return mPDimension;
    }

    public void setmPDimension(String mPDimension) {
        this.mPDimension = mPDimension;
    }

    public String getMpValue() {
        return mpValue;
    }

    public void setMpValue(String mpValue) {
        this.mpValue = mpValue;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPad_name() {
        return pad_name;
    }

    public void setPad_name(String pad_name) {
        this.pad_name = pad_name;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    @Override
    public int compareTo(PageItemBean pageItemBean) {
        int i  = Integer.valueOf(this.getPage()) - Integer.valueOf(pageItemBean.getPage()) ;
        return i;
    }
}
