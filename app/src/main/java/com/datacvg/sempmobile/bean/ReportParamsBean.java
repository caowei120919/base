package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

import java.util.ArrayList;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-12
 * @Description :
 */
@Keep
public class ReportParamsBean {

    /**
     * params : [{"type":"time","unit":"day","timeShow":"point","timeFormat":"YYYYMMDD","startTime":10,"endTime":1,"id":"param1"}]
     */

    private String params;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public class ParamsResultListBean extends ArrayList<ParamsResultBean> {

    }


    public class ParamsResultBean{

        /**
         * type : time
         * unit : day
         * timeShow : point
         * timeFormat : YYYYMMDD
         * startTime : 10
         * endTime : 1
         * id : param1
         */

        private String type;
        private String unit;
        private String timeShow;
        private String timeFormat;
        private String startTime;
        private String endTime;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTimeShow() {
            return timeShow;
        }

        public void setTimeShow(String timeShow) {
            this.timeShow = timeShow;
        }

        public String getTimeFormat() {
            return timeFormat;
        }

        public void setTimeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
