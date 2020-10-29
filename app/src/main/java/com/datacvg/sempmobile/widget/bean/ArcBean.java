package com.datacvg.sempmobile.widget.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-28
 * @Description : 仪表盘绘制
 */
@Keep
public class ArcBean {
    /**
     * 起始角度
     */
    private float startAngle ;

    public ArcBean(float startAngle, float sweepAngle) {
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
    }

    public ArcBean() {

    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    /**
     * 转动角度
     */
    private float sweepAngle ;
}
