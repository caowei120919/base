package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-14
 * @Description :登录时返回的用户信息
 */
@Keep
public class UserLoginBean {

    /**
     * orgName : 上海数聚股份软件系统有限公司
     * userName : 舒婷
     * defaulttime : 202004
     * orgEnName : DATACVG
     * userPkid : 1691179324035185017442
     * userId : shuting
     */

    private String orgName;
    private String userName;
    private String defaulttime;
    private String orgEnName;
    private String userPkid;
    private String userId;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDefaulttime() {
        return defaulttime;
    }

    public void setDefaulttime(String defaulttime) {
        this.defaulttime = defaulttime;
    }

    public String getOrgEnName() {
        return orgEnName;
    }

    public void setOrgEnName(String orgEnName) {
        this.orgEnName = orgEnName;
    }

    public String getUserPkid() {
        return userPkid;
    }

    public void setUserPkid(String userPkid) {
        this.userPkid = userPkid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
