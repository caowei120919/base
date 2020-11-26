package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-19
 * @Description : 大屏投放Message实体
 */
@Keep
public class WebSocketMessageBean {

    /**
     * clientIp : 5d1444c68e389a05dc91d2c8ad77c13c
     * code : 2000
     * deviceId : pc
     * message : 投屏成功
     * scIndexStatus : 0
     * scPlayInfo : {"edit_flag":1,"pkid":"862471780512780903275","create_time":"2020-11-13 14:03:35","screen_attribute":"{\"animationTime\":300,\"animationMode\":\"circulation\",\"animationEffect\":\"moveLeft\"}","view_flag":1,"sort_val":"","update_time":"2020-11-13 14:03:35","login_name":"barry.chen","update_user":"149492828209879757","img_path":"image_read/899550885498535606477","screen_name":"大屏20201114","screen_id":"862471780512780903275","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"horizontal\"}","delete_flag":1}
     * scPlayStatus : start
     * targetIp : EDE44BA676745B9B05FE6F02B236CAF5C76DF1B31477ADC53A737AB1916C1DB434DD3B49EFBE70400B25A649208D8B6E7E9EE22EC39AAC5DB76DC6C69CE8BA42D00A43A21F13292A841D753BF1C422CC
     */

    private String ip ;
    private String clientIp;
    private String code;
    private String deviceId;
    private String message;
    private String scIndexStatus;
    private String scPlayInfo;
    private String scPlayStatus;
    private String targetIp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getScIndexStatus() {
        return scIndexStatus;
    }

    public void setScIndexStatus(String scIndexStatus) {
        this.scIndexStatus = scIndexStatus;
    }

    public String getScPlayInfo() {
        return scPlayInfo;
    }

    public void setScPlayInfo(String scPlayInfo) {
        this.scPlayInfo = scPlayInfo;
    }

    public String getScPlayStatus() {
        return scPlayStatus;
    }

    public void setScPlayStatus(String scPlayStatus) {
        this.scPlayStatus = scPlayStatus;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }
}
