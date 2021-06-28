package com.datacvg.dimp.baseandroid.config;

public class Api {
    public static boolean isDevelop = false;

    /**
     * 接口地址
     */
    public static String BASE_URL = "";
    /**
     * 测试
     */
    public static final String BASE_URL_TEST = "http://testapp.wobianmall.com";
    /**
     * 正式
     */
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
}
