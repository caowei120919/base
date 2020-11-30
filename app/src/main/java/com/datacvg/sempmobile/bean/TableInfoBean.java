package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description : 报表配置信息
 */
@Keep
public class TableInfoBean {

    /**
     * reportType : TABLEAU
     * data : {}
     * removeCookie : ["XSRF-TOKEN","tableau_locale","workgroup_session_id"]
     * paramStr :
     * showUrl : http://192.168.2.181/trusted/-dg-x-5SSomX85LIkKQBEw==:2ARhiCECP3peX_5VAu9nq1yU/views/Superstore/CommissionModel?:toolbar=y&:view=y
     * resId : 935223758498158911255
     */

    private String reportType;
    private DataBean data;
    private String paramStr;
    private String showUrl;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public List<String> getRemoveCookie() {
        return removeCookie;
    }

    public void setRemoveCookie(List<String> removeCookie) {
        this.removeCookie = removeCookie;
    }

    private String resId;
    private List<String> removeCookie;

    public static class DataBean {
    }
}
