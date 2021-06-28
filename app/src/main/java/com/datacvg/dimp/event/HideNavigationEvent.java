package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-28
 * @Description :
 */
@Keep
public class HideNavigationEvent {
    private Boolean isHide = false ;

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public HideNavigationEvent(Boolean isHide) {
        this.isHide = isHide;
    }
}
