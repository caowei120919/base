package com.datacvg.sempmobile.baseandroid.config;

import androidx.annotation.Keep;

import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.datacvg.sempmobile.baseandroid.utils.PreferencesUtils;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 相关常量存放
 */
@Keep
public class Constants {

    public static final String DATA_CVG_BASE_URL = "http://datacvg.com/" ;
    public static String BASE_MOBILE_URL = "" ;
    public static String BASE_FIS_URL = "" ;
    public static String BASE_UPLOAD_URL = "" ;

    /**
     * 用于获取接口服务器地址的url
     */
    public static final String GET_SERVER_URL
            = "http://semp.datacvg.com/mobile/" + "%s" + "appinfo.json" ;

    /**
     * 河狸项目code地址
     */
    public static final String CUSTOM_CODE_URL = "http://semp.datacvg.com/mobile/%s-license.json" ;

    /**
     * 获取河狸code地址
     */
    public static final String CODE_FROM_SERVER_URL
            = "http://semp.datacvg.com/mobile/applicense.json" ;

    /**
     * 项目baseUrl,登录时获取api服务器地址使用,获取到api地址的时候使用新的替换掉
     */
    public static String BASE_URL = "http://datacvg.com/" ;

    /**
     * 大屏展示链接服务器的url，登录获取api服务器地址的时候,使用返回值代替
     */
    public static final String authorization = "Authorization" ;

    public static String token = "" ;

}
