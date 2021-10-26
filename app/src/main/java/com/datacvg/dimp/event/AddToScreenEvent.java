package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.ReportBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-26
 * @Description :
 */
@Keep
public class AddToScreenEvent {
    private ReportBean reportBean ;
    public AddToScreenEvent(ReportBean reportBean) {
        this.reportBean = reportBean ;
    }

    public ReportBean getReportBean() {
        return reportBean;
    }

    public void setReportBean(ReportBean reportBean) {
        this.reportBean = reportBean;
    }
}
