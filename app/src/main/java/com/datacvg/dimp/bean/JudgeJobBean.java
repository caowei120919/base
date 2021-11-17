package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-10
 * @Description :
 */
@Keep
public class JudgeJobBean implements Serializable {

    /**
     * errorInfo :
     * Semf-SsoToken : 75A2987E203894EAF3188F749D611EC7303CB827BC97688E33914EA194E4413C9D52E145368DE3B71C3F08EC7410E79B1C961DE1B27B88276F8B28ACB61E9635D69C8E108EF979908717D36B263D58AB
     * status : 200
     * errorMsg :
     */

    private String errorInfo;
    @SerializedName("Semf-SsoToken")
    private String SemfSsoToken;
    private String status;
    private String errorMsg;

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getSemfSsoToken() {
        return SemfSsoToken;
    }

    public void setSemfSsoToken(String semfSsoToken) {
        SemfSsoToken = semfSsoToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
