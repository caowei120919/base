package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-06
 * @Description :
 */
@Keep
public class SetDefaultResBean {

    /**
     * result : true
     * defaultPkid : 430226698940527443983
     */

    private Boolean result;
    private String defaultPkid;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getDefaultPkid() {
        return defaultPkid;
    }

    public void setDefaultPkid(String defaultPkid) {
        this.defaultPkid = defaultPkid;
    }
}
