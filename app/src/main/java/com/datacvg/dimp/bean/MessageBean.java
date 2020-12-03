package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 消息
 */
@Keep
public class MessageBean {

    /**
     * result : []
     * planning : 0
     * smartinsight : 0
     * business : 0
     * totalNum : 1073
     * permissions : ["3","5","1","2"]
     * report : 0
     * ttotal : 1060
     * action : 0
     * ftotal : 13
     */

    private Integer planning;
    private Integer smartinsight;
    private Integer business;
    private Integer totalNum;
    private Integer report;
    private Integer ttotal;
    private Integer action;
    private Integer ftotal;
    private List<ResultBean> result;
    private List<String> permissions;

    public Integer getPlanning() {
        return planning;
    }

    public void setPlanning(Integer planning) {
        this.planning = planning;
    }

    public Integer getSmartinsight() {
        return smartinsight;
    }

    public void setSmartinsight(Integer smartinsight) {
        this.smartinsight = smartinsight;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getTtotal() {
        return ttotal;
    }

    public void setTtotal(Integer ttotal) {
        this.ttotal = ttotal;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getFtotal() {
        return ftotal;
    }

    public void setFtotal(Integer ftotal) {
        this.ftotal = ftotal;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Keep
    public class ResultBean {
        private String id ;
        private String primary_id ;
        private String module_id ;
        private String send_user_id ;
        private String send_user_name ;
        private String receive_user_id ;
        private String receive_user_name ;
        private String message_title ;
        private String message_text ;
        private String message_time ;
        private boolean read_flag = false;
        private String read_time ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrimary_id() {
            return primary_id;
        }

        public void setPrimary_id(String primary_id) {
            this.primary_id = primary_id;
        }

        public String getModule_id() {
            return module_id;
        }

        public void setModule_id(String module_id) {
            this.module_id = module_id;
        }

        public String getSend_user_id() {
            return send_user_id;
        }

        public void setSend_user_id(String send_user_id) {
            this.send_user_id = send_user_id;
        }

        public String getSend_user_name() {
            return send_user_name;
        }

        public void setSend_user_name(String send_user_name) {
            this.send_user_name = send_user_name;
        }

        public String getReceive_user_id() {
            return receive_user_id;
        }

        public void setReceive_user_id(String receive_user_id) {
            this.receive_user_id = receive_user_id;
        }

        public String getReceive_user_name() {
            return receive_user_name;
        }

        public void setReceive_user_name(String receive_user_name) {
            this.receive_user_name = receive_user_name;
        }

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getMessage_text() {
            return message_text;
        }

        public void setMessage_text(String message_text) {
            this.message_text = message_text;
        }

        public String getMessage_time() {
            return message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }

        public boolean isRead_flag() {
            return read_flag;
        }

        public void setRead_flag(boolean read_flag) {
            this.read_flag = read_flag;
        }

        public String getRead_time() {
            return read_time;
        }

        public void setRead_time(String read_time) {
            this.read_time = read_time;
        }
    }
}
