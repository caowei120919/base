package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-19
 * @Description :
 */
@Keep
public class CheckVersionBean {

    /**
     * desc : 版本描述
     * download : http://semp.datacvg.com/mob/saas/DIMPMobile_standard.apk
     * enforce : 1
     * version : 1.0.0
     */

    private String desc;
    private String download;
    private String enforce;
    private String version;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getEnforce() {
        return enforce;
    }

    public void setEnforce(String enforce) {
        this.enforce = enforce;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
