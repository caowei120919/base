package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-02-09
 * @Description :
 */
@Keep
public class DigitalEditEvent {
    /**
     * 是否展示底部
     */
    private Boolean isShowBottom = false ;

    public DigitalEditEvent(Boolean isShowBottom) {
        this.isShowBottom = isShowBottom;
    }

    public Boolean getShowBottom() {
        return isShowBottom;
    }

    public void setShowBottom(Boolean showBottom) {
        isShowBottom = showBottom;
    }
}
