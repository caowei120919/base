package com.datacvg.dimp.fragment;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-15
 * @Description :
 */
@Keep
public class CheckPageNameEvent {
    private String padName ;
    public CheckPageNameEvent(String padName) {
        this.padName = padName ;
    }

    public String getPadName() {
        return padName;
    }

    public void setPadName(String padName) {
        this.padName = padName;
    }
}
