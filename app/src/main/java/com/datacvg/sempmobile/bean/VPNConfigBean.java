package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-23
 * @Description : vpn配置实体
 */
@Keep
public class VPNConfigBean implements Serializable {
    @SerializedName("semf-url")
    private String semfurl ;
    @SerializedName("license-url")
    private String licenseurl ;
    @SerializedName("vpn-model")
    private String vpnmodel ;
    @SerializedName("vpn-url")
    private String vpnurl ;
    @SerializedName("vpn-user")
    private String vpnuser ;
    @SerializedName("vpn-password")
    private String vpnpassword ;

    public VPNConfigBean() {

    }

    public String getSemfurl() {
        return semfurl;
    }

    public void setSemfurl(String semfurl) {
        this.semfurl = semfurl;
    }

    public String getLicenseurl() {
        return licenseurl;
    }

    public void setLicenseurl(String licenseurl) {
        this.licenseurl = licenseurl;
    }

    public String getVpnmodel() {
        return vpnmodel;
    }

    public void setVpnmodel(String vpnmodel) {
        this.vpnmodel = vpnmodel;
    }

    public String getVpnurl() {
        return vpnurl;
    }

    public void setVpnurl(String vpnurl) {
        this.vpnurl = vpnurl;
    }

    public String getVpnuser() {
        return vpnuser;
    }

    public void setVpnuser(String vpnuser) {
        this.vpnuser = vpnuser;
    }

    public String getVpnpassword() {
        return vpnpassword;
    }

    public void setVpnpassword(String vpnpassword) {
        this.vpnpassword = vpnpassword;
    }
}
