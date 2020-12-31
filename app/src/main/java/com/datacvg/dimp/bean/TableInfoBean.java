package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description : 报表配置信息
 */
@Keep
public class TableInfoBean {

    /**
     * reportType : TABLEAU
     * data : {}
     * removeCookie : ["XSRF-TOKEN","tableau_locale","workgroup_session_id"]
     * paramStr :
     * showUrl : http://192.168.2.181/trusted/-dg-x-5SSomX85LIkKQBEw==:2ARhiCECP3peX_5VAu9nq1yU/views/Superstore/CommissionModel?:toolbar=y&:view=y
     * resId : 935223758498158911255
     */

    private String reportType;
    private DataBean data;
    private String paramStr;
    private String showUrl;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public List<String> getRemoveCookie() {
        return removeCookie;
    }

    public void setRemoveCookie(List<String> removeCookie) {
        this.removeCookie = removeCookie;
    }

    private String resId;
    private List<String> removeCookie;

    public static class DataBean {

        /**
         * reportId : d8a0ed00-f5d4-4ad6-87d5-0cb1d2f87654
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6ImZwb19BS0hTc0V6c2Ezam43Y1V4bExlY3VUayIsImtpZCI6ImZwb19BS0hTc0V6c2Ezam43Y1V4bExlY3VUayJ9.eyJhdWQiOiJodHRwczovL2FuYWx5c2lzLmNoaW5hY2xvdWRhcGkuY24vcG93ZXJiaS9hcGkiLCJpc3MiOiJodHRwczovL3N0cy5jaGluYWNsb3VkYXBpLmNuL2NhZmEyYzg0LTNhNGUtNDhjZi05MTdjLWNlZjlmZGMyZTVlNy8iLCJpYXQiOjE2MDg2MDQ0NzYsIm5iZiI6MTYwODYwNDQ3NiwiZXhwIjoxNjA4NjA4Mzc2LCJhY2N0IjowLCJhY3IiOiIxIiwiYWlvIjoiQVNRQTIvOEpBQUFBeUExQXFuS201TC9hdXJhUXlrM1pTS0tQeFdaY1lBdXlodUcvYXpDU1owYz0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNDE4ZWMzNDQtYjNiZC00ZjcwLTk2ZTktYTJhN2ZjZTE4MTE1IiwiYXBwaWRhY3IiOiIxIiwiZmFtaWx5X25hbWUiOiLolKEiLCJnaXZlbl9uYW1lIjoi5LyfIiwiaXBhZGRyIjoiMTgwLjE2OS45NC4yNTMiLCJuYW1lIjoi5LyfIOiUoSIsIm9pZCI6IjI1ZDkzZGE2LWVlZmYtNDJhMS1iYjc1LWI3ZjM1YTcyOWZiMSIsInB1aWQiOiIyMDAzQkZGRDgxN0Y2NUZCIiwicmgiOiIwLkFBQUFoQ3o2eWs0NnowaVJmTTc1X2NMbDUwVERqa0c5czNCUGx1bWlwX3poZ1JVQ0FFdy4iLCJzY3AiOiJDYXBhY2l0eS5SZWFkLkFsbCBDYXBhY2l0eS5SZWFkV3JpdGUuQWxsIENvbnRlbnQuQ3JlYXRlIERhc2hib2FyZC5SZWFkLkFsbCBEYXNoYm9hcmQuUmVhZFdyaXRlLkFsbCBEYXRhLkFsdGVyX0FueSBEYXRhc2V0LlJlYWQuQWxsIERhdGFzZXQuUmVhZFdyaXRlLkFsbCBHcm91cC5SZWFkIEdyb3VwLlJlYWQuQWxsIE1ldGFkYXRhLlZpZXdfQW55IFJlcG9ydC5SZWFkLkFsbCBSZXBvcnQuUmVhZFdyaXRlLkFsbCBUZW5hbnQuUmVhZC5BbGwgVGVuYW50LlJlYWRXcml0ZS5BbGwgV29ya3NwYWNlLlJlYWQuQWxsIFdvcmtzcGFjZS5SZWFkV3JpdGUuQWxsIiwic3ViIjoieS00T0JVN1pYQW9heW56WGJyeWxWZ3RBOVNyT2NJUTcyR3ZXTGU2VWZNSSIsInRpZCI6ImNhZmEyYzg0LTNhNGUtNDhjZi05MTdjLWNlZjlmZGMyZTVlNyIsInVuaXF1ZV9uYW1lIjoid2VpLmNhaUBkYXRhY3ZnZGV2LnBhcnRuZXIub25tc2NoaW5hLmNuIiwidXBuIjoid2VpLmNhaUBkYXRhY3ZnZGV2LnBhcnRuZXIub25tc2NoaW5hLmNuIiwidXRpIjoiVXpXOFhYczEta2kyTm15SXZxWVpBQSIsInZlciI6IjEuMCIsIndpZHMiOlsiNjJlOTAzOTQtNjlmNS00MjM3LTkxOTAtMDEyMTc3MTQ1ZTEwIiwiYjc5ZmJmNGQtM2VmOS00Njg5LTgxNDMtNzZiMTk0ZTg1NTA5Il19.TkR4_Y--4JSsSK8GoCVNUMpzSym6trjjm-cn_86SpHZAu9Pi8BFJZYl-B3GPfh8i6uAGgH3MbIfebKu2xctXCXwuqUdAVUJ6j8TwiTRHWE8k4EOtHWxLgktSnrflu2zZ3TRXVV4UtCF2sbgpTNGsfubzH3vCCeRmelGE6im9Tpiagx4RopF2mrcM-LtfpmM6zx0amkaDXzcRuZhjs37LPnb4wNZ5qS-F33ZvXp7SQ7EDavubNux6v9ynaPY4G8rkCVyBVDxH8rDZxE3Ts5nxVlFtGCijb9sXE2YRijpYoyY80IoNMqeP-AQOj8aTTJy899eYQANGfv84_K_mfz6L9A
         */

        private String reportId;
        private String token;

        public String getReportId() {
            return reportId;
        }

        public void setReportId(String reportId) {
            this.reportId = reportId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
