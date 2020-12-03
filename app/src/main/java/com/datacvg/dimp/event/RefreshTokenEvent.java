package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-14
 * @Description : token过期事件通知
 */
@Keep
public class RefreshTokenEvent implements Serializable {
    public RefreshTokenEvent() {

    }
}
