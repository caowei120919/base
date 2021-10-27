package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-26
 * @Description :
 */
@Keep
public class AddToScreenRequestBean implements Serializable {

    /**
     * reportType : model_report
     * reportId : 1269639719473886658302
     * screenId : 526720545675996605375
     * screenName : 管理画布添加新屏
     * basicAttr : {"loadTime":20,"loadTimeUnit":"minute","stayTime":300,"stayUnit":"second","animationTime":300,"animationEffect":"fadeInAndOut"}
     */

    private String reportType;
    private String reportId;
    private String screenId;
    private String screenName;
    private String basicAttr;
    private String screenFormat;
    private String type ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScreenFormat() {
        return screenFormat;
    }

    public void setScreenFormat(String screenFormat) {
        this.screenFormat = screenFormat;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getBasicAttr() {
        return basicAttr;
    }

    public void setBasicAttr(String basicAttr) {
        this.basicAttr = basicAttr;
    }
}
