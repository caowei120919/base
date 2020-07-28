package com.datacvg.sempmobile.baseandroid.retrofit.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 返回数据基础格式
 *
 *      * message : ok
 *      * status : 1
 *      * user_token : ccdb281cdac7aa5c92650cb5bcafaf30168c8aae
 *      * resdata : {}
 *
 */
@Keep
public class BaseBean<T> {
    private String message ;
    private String user_token ;
    private int status ;
    private T resdata ;

    public String getMessage() {
        return message;
    }

    public String getUser_token() {
        return user_token;
    }

    public int getStatus() {
        return status;
    }

    public T getResdata() {
        return resdata;
    }
}
