package com.datacvg.dimp.baseandroid.config;

public class Api {
    public static boolean isDevelop = false;

    /**
     * 接口地址
     */
    public static String BASE_URL = "";
    //测试
    public static final String BASE_URL_TEST = "http://testapp.wobianmall.com";
    //正式
    public static final String BASE_URL_PRODUCT = "http://app.wobianmall.com";


    /**
     * environment切换环境：
     * 1：测试环境；
     * 2：预发布环境；
     * 3: 线上环境;
     */
    public enum AppEnviron {
        DEV_TEST_ENV(1),
        REL_PRE_ENV(2);

        private int value;

        private AppEnviron(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static AppEnviron of(int intValue) {

            switch (intValue) {

                case 1:
                    return DEV_TEST_ENV;

                default:
                    return REL_PRE_ENV;
            }
        }
    }

    public static void setEnviroment(int env) {

        switch (AppEnviron.of(env)) {

            case DEV_TEST_ENV:
                isDevelop = true;
                BASE_URL = BASE_URL_TEST;
                break;

            case REL_PRE_ENV:
                isDevelop = false;
                BASE_URL = BASE_URL_PRODUCT;
                break;

            default:
                break;
        }
    }

    public static class HEADER {

        //token
        public static final String HEADER_TOKEN = "token";
        //数字签名
        public static final String HEADER_SIGN = "sign";
        /**
         * 设备类型
         */
        public static final String HEADER_OS = "device";
        public static final String VALUE_OS = "android";

        /**
         * app版本
         */
        public static final String HEADER_VERSION = "app";
        /**
         * 版本CODE
         */
        public static final String HEADER_VERSIONCODE = "versionCode";
        /**
         * 设备唯一编号
         */
        public static final String HEADER_DEVICEID = "deviceid";
        /**
         * 用户票据
         */
        public static final String HEADER_TICKET = "kylin-video-ticket";
        /**
         * Cookie
         */
        public static final String HEADER_COOKIE = "Cookie";
        /**
         * channel 渠道
         */
        public static final String HEADER_CHANNEL = "channel";

        /**
         * 官方版app传1 广告版app传2
         */
        public static final String HEADER_APP_CHANNEL = "appChannel";
        public static final String APPCHANNEL_OFFICIAL = "1";//官方版app传1
        public static final String APPCHANNEL_AD = "2";     //广告版app传2


        /**
         * 手机 IMEI
         */
        public static final String IMEI = "imei";

        /**
         * 手机 MAC
         */
        public static final String MAC = "mac";


        /**
         * 手机 AndroidID
         */
        public static final String ANDROIDID = "androidid";

        /**
         * APP简称 区分是什么应用
         */
        public static final String APPNAME = "appName";

        /**
         * 手机系统版本号
         */
        public static final String SYSTEMVERSION = "os";

        /**
         * 手机型号
         */
        public static final String MODELNO = "modelNo";

        /**
         * 手机厂商
         */
        public static final String MANUFACTURER = "manufacturer";

    }
}
