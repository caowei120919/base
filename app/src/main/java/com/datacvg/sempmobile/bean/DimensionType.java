package com.datacvg.sempmobile.bean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-19
 * @Description : 维度类型
 */
public enum DimensionType {
    ORG("org"),
    PRO("pro"),
    AREA("area");

    private String type;

    DimensionType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return type == null ? "" : type;
    }
}
