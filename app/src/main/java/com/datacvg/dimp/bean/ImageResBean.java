package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :   图片二进制流
 */
@Keep
public class ImageResBean {


    /**
     * message : success
     * resdata : data:image/png;base64,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
     */

    private String message;
    private String resdata;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResdata() {
        return resdata;
    }

    public void setResdata(String resdata) {
        this.resdata = resdata;
    }
}