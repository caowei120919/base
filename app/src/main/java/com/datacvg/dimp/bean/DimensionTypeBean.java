package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-08
 * @Description :
 */
@Keep
public class DimensionTypeBean {
    private String name ;
    private String dimensionId ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }
}
