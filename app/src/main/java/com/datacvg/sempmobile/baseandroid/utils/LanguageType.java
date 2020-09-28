package com.datacvg.sempmobile.baseandroid.utils;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-28
 * @Description :
 */
public enum LanguageType {
    AUTO("auto"),
    CHINESE("ch"),
    ENGLISH("en"),
    THAILAND("th");

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language == null ? "" : language;
    }
}
