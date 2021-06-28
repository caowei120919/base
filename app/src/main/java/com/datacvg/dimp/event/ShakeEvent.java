package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-01
 * @Description :
 */
@Keep
public class ShakeEvent {
    private boolean isShake ;
    public ShakeEvent(boolean isShake) {
        this.isShake = isShake ;
    }

    public boolean isShake() {
        return isShake;
    }

    public void setShake(boolean shake) {
        isShake = shake;
    }
}
