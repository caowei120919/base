package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-06
 * @Description :
 */
@Keep
public class ConstantReportBean {
    private String resId ;
    private String PKId ;

    public ConstantReportBean(String res_id, String default_pkid) {
        this.resId = res_id ;
        this.PKId = default_pkid ;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getPKId() {
        return PKId;
    }

    public void setPKId(String PKId) {
        this.PKId = PKId;
    }
}
