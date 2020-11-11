package com.datacvg.sempmobile.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 未读消息改变事件
 */
@Keep
public class ChangeUnReadMessageEvent {
    private int total = 0 ;

    public ChangeUnReadMessageEvent(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
