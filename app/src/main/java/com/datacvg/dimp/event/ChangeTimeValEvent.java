package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
@Keep
public class ChangeTimeValEvent {
    private String mTimeValue ;

    public String getmTimeValue() {
        return mTimeValue;
    }

    public void setmTimeValue(String mTimeValue) {
        this.mTimeValue = mTimeValue;
    }

    public ChangeTimeValEvent(String mTimeValue) {
        this.mTimeValue = mTimeValue ;
    }
}
