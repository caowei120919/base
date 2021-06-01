package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-01
 * @Description :
 */
@Keep
public class DeletePageEvent {
    private String title ;

    public DeletePageEvent(String title) {
        this.title = title;
    }

    public DeletePageEvent() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
