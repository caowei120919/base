package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-25
 * @Description :
 */
@Keep
public class AddToScreenAttrBean {

    /**
     * loadTime : 20
     * loadTimeUnit : minute
     * stayTime : 300
     * stayUnit : second
     * animationTime : 300
     * animationEffect : fadeInAndOut
     */

    private Integer loadTime;
    private String loadTimeUnit;
    private Integer stayTime;
    private String stayUnit;
    private Integer animationTime;
    private String animationEffect;
    private String animationMode ;

    public String getAnimationMode() {
        return animationMode;
    }

    public void setAnimationMode(String animationMode) {
        this.animationMode = animationMode;
    }

    public Integer getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Integer loadTime) {
        this.loadTime = loadTime;
    }

    public String getLoadTimeUnit() {
        return loadTimeUnit;
    }

    public void setLoadTimeUnit(String loadTimeUnit) {
        this.loadTimeUnit = loadTimeUnit;
    }

    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    }

    public String getStayUnit() {
        return stayUnit;
    }

    public void setStayUnit(String stayUnit) {
        this.stayUnit = stayUnit;
    }

    public Integer getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(Integer animationTime) {
        this.animationTime = animationTime;
    }

    public String getAnimationEffect() {
        return animationEffect;
    }

    public void setAnimationEffect(String animationEffect) {
        this.animationEffect = animationEffect;
    }
}
