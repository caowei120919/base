package com.datacvg.dimp.baseandroid.config;

import androidx.annotation.Keep;

import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.UserLoginBean;
import com.datacvg.dimp.bean.WebSocketLinkBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 相关常量存放
 */
@Keep
public class Constants {

    public static final String EXTRA_DATA_FOR_BEAN = "EXTRA_DATA_FOR_BEAN" ;
    public static final String EXTRA_DATA_FOR_ALBUM = "EXTRA_DATA_FOR_ALBUM";
    public static final String EXTRA_DATA_FOR_SCAN = "EXTRA_DATA_FOR_SCAN";
    public static final String DIMENSION_FIRST = "DIMENSION_FIRST";
    public static final String DIMENSION_SECOND = "DIMENSION_SECOND";
    public static final String DIMENSION_THIRD = "DIMENSION_THIRD";

    /**
     * 选择联系人类型
     */
    public static int CHOOSE_TYPE_HEAD = 0x00001029 ;
    public static int CHOOSE_TYPE_ASSISTANT = 0x00001028 ;

    public static final int SCAN_FOR_VPN = 0x00001027 ;
    public static final int SCAN_FOR_SCREEN = 0x00001026 ;
    public static final int SCAN_FOR_LOGIN = 0x00001025 ;

    public static final int REQUEST_TAKE_PHOTO = 0x00001024 ;
    public static final int REQUEST_OPEN_CAMERA = 0x00001023 ;
    public static final int RESULT_SCAN_RESULT = 0x00001022 ;

    public static final String DATA_CVG_BASE_URL = "http://%s/datacvg.com/" ;
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_PKID = "USER_PKID";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_ORG_NAME = "USER_ORG_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_DEFAULT_TIME = "USER_DEFAULT_TIME";
    public static final String USER_DEFAULT_MONTH = "USER_DEFAULT_MONTH";
    public static final String USER_DEFAULT_DAY = "USER_DEFAULT_DAY";
    public static final String USER_DEFAULT_YEAR = "USER_DEFAULT_YEAR";
    public static final String USER_ORG_EN_NAME = "USER_ORG_EN_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String USER_COMPANY_CODE = "USER_COMPANY_CODE";
    public static final String USER_CHECK_REMEMBER = "USER_CHECK_REMEMBER";
    public static final String LANG = "LANG";
    public static final String DESC = "desc";
    public static final String ASC = "asc";
    public static final String TABLE_TYPE = "3";
    public static String BASE_MOBILE_URL = "" ;
    public static String BASE_FIS_URL = "" ;
    public static String BASE_UPLOAD_URL = "" ;
    public static String BASE_DDB_URL = "" ;
    public static String HEAD_IMG_URL = "api/mobile/login/readheadimg?userPkid=" ;

    public final static int SERVICE_CODE_SUCCESS_MOBILE = 1 ;
    public final static int SERVICE_CODE_SUCCESS_FIS = 2000 ;
    public final static int SERVICE_CODE_FAIL_FOR_TOKEN = 401 ;

    /**
     * 大屏投放
     */
    /**
     *启动
     */
    public static final String SCREEN_BOOT = "boot";
    /**
     *退出
     */
    public static final String SCREEN_CLOSE = "close";
    /**
     *询问
     */
    public static final String SCREEN_REQUEST = "request";
    /**
     *播放
     */
    public static final String SCREEN_START = "start";
    /**
     * 暂停
     */
    public static final String SCREEN_PAUSE = "pause";
    /**
     * 下一张
     */
    public static final String SCREEN_NEXT = "next";
    /**
     *上一张
     */
    public static final String SCREEN_UPPER = "upper";
    /**
     * 默认播放位置
     */
    public static final String DEFAULT_POSITION = "0";
    /**
     * 正常操作
     */
    public static final String COMMON_CODE = "2000" ;

    /**
     * 实时删除
     */
    public static final String DELETE_CODE = "2004" ;
    /**
     * 实时添加
     */
    public static final String ADD_CODE = "2004" ;
    /**
     * 当前大屏页信息
     */
    public static ScreenBean screenBean ;

    /**
     * 应用语言
     */
    public final static String APP_LANGUAGE = "APP_LANGUAGE" ;
    public final static String LANGUAGE_CHINESE = "zh" ;
    public final static String LANGUAGE_ENGLISH = "en" ;
    public final static String LANGUAGE_AUTO = "auto" ;

    /**
     * 报表参数选择类型
     *      DIMENSION 维度选择
     *      CUSTOM  级联选择
     *      TIME  时间选择
     */
    public final static String REPORT_PARAMS_TIME = "TIME" ;
    public final static String REPORT_PARAMS_CUSTOM = "CUSTOM" ;
    public final static String REPORT_PARAMS_DIMENSION = "DIMENSION" ;

    /**
     * 报告展示形式
     */
    public final static String REPORT_GRID = "REPORT_GRID" ;
    public final static String REPORT_LIST = "REPORT_LIST" ;
    public static final String REPORT_MINE = "MODEL";
    public static final String REPORT_SHARE = "SHARE";

    /**
     * 消息权限类型
     */
    /**
     * 行动方案消息
     */
    public final static String MSG_ACTION = "1" ;
    /**
     * 警告消息
     */
    public final static String MSG_WARN = "2" ;
    /**
     * 经营看板消息
     */
    public final static String MSG_DASHBOARD = "5" ;
    /**
     * 报表评论消息
     */
    public final static String MSG_REPORTCOMMENT = "3" ;

    public final static String DO_READ = "do_read";
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


    public static final String BASE_LOCAL_URL = "http://dimp.dev.datacvg.com/api/mobile";
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
    public static String BASE_URL = "https://datacvg.datacvg.com/" ;

    public static String BASE_MERCHANT = "https://"+ "%s" +".datacvg.com/";

    /**
     * 大屏展示链接服务器的url，登录获取api服务器地址的时候,使用返回值代替
     */
    public static final String AUTHORIZATION = "Authorization" ;

    public static String token = "" ;

    /**
     * 分页最大数量
     */
    public static int MAX_PAGE_SIZE = 10000 ;
    /**
     * 分页默认每页数量
     */
    public static int DEFAULT_PAGE_SIZE = 20 ;


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

    /**
     * 暂存链接websocket的大屏信息
     */
    public static List<WebSocketLinkBean> linkBeans = new ArrayList<>();
}
