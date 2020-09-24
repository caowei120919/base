package com.datacvg.sempmobile.baseandroid.greendao.bean;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-24
 * @Description :
 */
@Keep
@Entity
public class VpnInfo {

    @Id(autoincrement = true)
    private Long id ;

    private String semfurl ;

    private String licenseurl ;

    private String vpnmodel ;

    private String vpnurl ;

    private String vpnuser ;

    private String vpnpassword ;

    @Generated(hash = 739554472)
    public VpnInfo(Long id, String semfurl, String licenseurl, String vpnmodel,
            String vpnurl, String vpnuser, String vpnpassword) {
        this.id = id;
        this.semfurl = semfurl;
        this.licenseurl = licenseurl;
        this.vpnmodel = vpnmodel;
        this.vpnurl = vpnurl;
        this.vpnuser = vpnuser;
        this.vpnpassword = vpnpassword;
    }

    @Generated(hash = 1990561865)
    public VpnInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemfurl() {
        return this.semfurl;
    }

    public void setSemfurl(String semfurl) {
        this.semfurl = semfurl;
    }

    public String getLicenseurl() {
        return this.licenseurl;
    }

    public void setLicenseurl(String licenseurl) {
        this.licenseurl = licenseurl;
    }

    public String getVpnmodel() {
        return this.vpnmodel;
    }

    public void setVpnmodel(String vpnmodel) {
        this.vpnmodel = vpnmodel;
    }

    public String getVpnurl() {
        return this.vpnurl;
    }

    public void setVpnurl(String vpnurl) {
        this.vpnurl = vpnurl;
    }

    public String getVpnuser() {
        return this.vpnuser;
    }

    public void setVpnuser(String vpnuser) {
        this.vpnuser = vpnuser;
    }

    public String getVpnpassword() {
        return this.vpnpassword;
    }

    public void setVpnpassword(String vpnpassword) {
        this.vpnpassword = vpnpassword;
    }
}
