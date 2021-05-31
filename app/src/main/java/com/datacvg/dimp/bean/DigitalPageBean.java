package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-28
 * @Description :
 */
@Keep
public class DigitalPageBean {

    /**
     * positionPage : [{"page":"16","pad_name":"{default}1621412937419","dimensions":"[\"14860367656855969470\"]","time_type":"month"},{"page":"14","pad_name":"财务","dimensions":"[\"14860367656855969470\",\"118306192070461277956\",\"118341583624371459776\"]","time_type":"month"},{"page":"17","pad_name":"{default}1621923812277","dimensions":"[\"14860367656855969470\",\"118306192070461277956\",\"118341583624371459776\"]","time_type":"month"},{"page":"15","pad_name":"{default}1621410802067","dimensions":"[\"14860367656855969470\"]","time_type":"month"},{"page":"18","pad_name":"{default}1621923712046","dimensions":"[\"14860367656855969470\"]","time_type":"month"}]
     */

    private String positionPage;

    public String getPositionPage() {
        return positionPage;
    }

    public void setPositionPage(String positionPage) {
        this.positionPage = positionPage;
    }
}
