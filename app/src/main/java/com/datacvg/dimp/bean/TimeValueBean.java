package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-03-26
 * @Description :
 */
@Keep
public class TimeValueBean {

    /**
     * defaultTime : {"month":"2021/01","year":"2019","defaultTime":"2021/01","day":"2021/03/25"}
     */

    private DefaultTimeBean defaultTime;

    public DefaultTimeBean getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(DefaultTimeBean defaultTime) {
        this.defaultTime = defaultTime;
    }

    public static class DefaultTimeBean {
        /**
         * month : 2021/01
         * year : 2019
         * defaultTime : 2021/01
         * day : 2021/03/25
         */

        private String month;
        private String year;
        private String defaultTime;
        private String day;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getDefaultTime() {
            return defaultTime;
        }

        public void setDefaultTime(String defaultTime) {
            this.defaultTime = defaultTime;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
