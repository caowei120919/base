package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-20
 * @Description : 链接中大屏信息
 */
@Keep
public class WebSocketLinkBean {
    private String screenId ;
    private String targetIp ;

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    private String screenTime ;
    private int currentPosition = 0 ;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public WebSocketLinkBean(String screenId, String screenTime) {
        this.screenId = screenId ;
        this.screenTime = screenTime ;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(String screenTime) {
        this.screenTime = screenTime;
    }
}
