package com.datacvg.sempmobile.baseandroid.config;

import androidx.annotation.Keep;

import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.bean.UserLoginBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 相关常量存放
 */
@Keep
public class Constants {

    public static final String EXTRA_DATA_FOR_BEAN = "EXTRA_DATA_FOR_BEAN" ;

    public static final int REQUEST_TAKE_PHOTO = 0x00001024 ;
    public static final int REQUEST_OPEN_CAMERA = 0x00001023 ;
    public static final int RESULT_SCAN_RESULT = 0x00001022 ;

    public static final String DATA_CVG_BASE_URL = "http://datacvg.com/" ;
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_PKID = "USER_PKID";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_ORG_NAME = "USER_ORG_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_DEFAULT_TIME = "USER_DEFAULT_TIME";
    public static final String USER_ORG_EN_NAME = "USER_ORG_EN_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String USER_COMPANY_CODE = "USER_COMPANY_CODE";
    public static final String USER_CHECK_REMEMBER = "USER_CHECK_REMEMBER";
    public static String BASE_MOBILE_URL = "" ;
    public static String BASE_FIS_URL = "" ;
    public static String BASE_UPLOAD_URL = "" ;

    public final static int SERVICE_CODE_SUCCESS_MOBILE = 1 ;
    public final static int SERVICE_CODE_SUCCESS_FIS = 2000 ;
    public final static int SERVICE_CODE_FAIL_FOR_TOKEN = 401 ;

    /**
     * 应用语言
     */
    public final static String APP_LANGUAGE = "APP_LANGUAGE" ;
    public final static String LANGUAGE_CHINESE = "zh" ;
    public final static String LANGUAGE_ENGLISH = "en" ;
    public final static String LANGUAGE_AUTO = "auto" ;

    /**
     * vpn模式
     */
    public final static String VPN_MODEL_EASY = "easyapp" ;
    public final static String VPN_MODEL_L3 = "l3vpn" ;
    public final static String VPN_SEMF_URL = "VPN_SEMF_URL" ;
    public final static String VPN_LICENSE_URL = "VPN_LICENSE_URL" ;
    public final static String VPN_MODEL = "VPN_MODEL" ;
    public final static String VPN_URL = "VPN_URL" ;
    public final static String VPN_USER = "VPN_USER" ;
    public final static String VPN_PASSWORD = "VPN_PASSWORD" ;

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

    /**
     *
     * @param user  用户信息实体
     * @param checked   是否选中记住用户密码
     * @param password  用户密码
     * @param companyCode  企业标识码
     */
    public static void saveUser(UserLoginBean user, boolean checked
            , String password,String companyCode) {
        PreferencesHelper.put(USER_PKID,user.getUserPkid());
        PreferencesHelper.put(USER_ORG_NAME,user.getOrgName());
        PreferencesHelper.put(USER_NAME,user.getUserName());
        PreferencesHelper.put(USER_DEFAULT_TIME,user.getDefaulttime());
        PreferencesHelper.put(USER_ORG_EN_NAME,user.getOrgEnName());
        PreferencesHelper.put(USER_ID,user.getUserId());
        if(checked){
            PreferencesHelper.put(USER_COMPANY_CODE,companyCode);
            PreferencesHelper.put(USER_PWD,password);
        }
        PreferencesHelper.put(USER_CHECK_REMEMBER,checked);
    }
}
