package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description :
 */
@Keep
public class TableParamInfoListBean {

    private List<TableParamInfoBean> ReportInitParam;

    public List<TableParamInfoBean> getReportInitParam() {
        return ReportInitParam;
    }

    public void setReportInitParam(List<TableParamInfoBean> reportInitParam) {
        ReportInitParam = reportInitParam;
    }
}
