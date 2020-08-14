package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-14
 * @Description : 用户对象实体类，只对外提供get方法，保持数据的统一性
 */
@Keep
public class UserModel {
    private String orgName;
    private String userName;
    private String defaulttime;
    private String orgEnName;
    private String userPkid;
    private String userId;
    private String userToken;
    private String userCompanyCode ;
    private boolean isRememberUser = false ;

    public String getOrgName() {
        return orgName;
    }

    public String getUserName() {
        return userName;
    }

    public String getDefaulttime() {
        return defaulttime;
    }

    public String getOrgEnName() {
        return orgEnName;
    }

    public String getUserPkid() {
        return userPkid;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getUserCompanyCode() {
        return userCompanyCode;
    }
}
