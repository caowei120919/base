package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
@Keep
public class Arc {
    /**
     * 起始角度
     */
    float startAngle = 0 ;

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    /**
     * 结束角度
     */
    float endAngle = 0 ;
}
