package com.datacvg.sempmobile.baseandroid.utils;

import android.os.Build;

/**
 * FileName: ApiLevel
 * Author: 曹伟
 * Date: 2019/9/12 17:11
 * Description:
 */

public class ApiLevel {

    /**
     * @param level minimum API level version that has to support the device
     * @return true when the caller API version is at least level
     */
    public static boolean require(int level) {
        return Build.VERSION.SDK_INT >= level;
    }

    /**
     * @return true when the caller API version is at least Froyo 8
     */
    public static boolean requireFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * @return true when the caller API version is at least GingerBread 9
     */
    public static boolean requireGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * @return true when the caller API version is at least Honeycomb 11
     */
    public static boolean requireHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * @return true when the caller API version is at least Honeycomb 13
     */
    public static boolean requireHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * @return true when the caller API version is at least ICS 14
     */
    public static boolean requireIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * @return true when the caller API version is at least JellyBean 16
     */
    public static boolean requireJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * @return true when the caller API version is at least JellyBean MR1 17
     */
    public static boolean requireJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * @return true when the caller API version is at least JellyBean MR2 18
     */
    public static boolean requireJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * @return true when the caller API version is at least Kitkat 19
     */
    public static boolean requireKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * @return true when the caller API version is at least Lollipop 21
     */
    public static boolean requireLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * @return true when the caller API version is at least Lollipop MR1 22
     */
    public static boolean requireLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * @return true when the caller API version is at least Marshmallow 23
     */
    public static boolean requireMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * @return true when the caller API version is at least Nougat 24
     */
    public static boolean requireNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * @return true when the caller API version is at least Nougat 25
     */
    public static boolean requireNougatMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * @return true when the caller API version is at least Oreo 26
     */
    public static boolean requireOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * @return true when the caller API version is at least OreoMR1 27
     */
    /*public static boolean requireOreoMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }*/
}
