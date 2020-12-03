package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-30
 * @Description :
 */
@Keep
public class RefreshTableEvent {
    private String paramArr ;

    public String getParamArr() {
        return paramArr;
    }

    public void setParamArr(String paramArr) {
        this.paramArr = paramArr;
    }

    public RefreshTableEvent(String paramArr) {
        this.paramArr = paramArr ;
    }
}
