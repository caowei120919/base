package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-02
 * @Description :
 */
@Keep
public class ActionForIndexBean implements Serializable {
    private List<ActionIndexBean> searchActionPlan = new ArrayList<>();

    public List<ActionIndexBean> getSearchActionPlan() {
        return searchActionPlan;
    }

    public void setSearchActionPlan(List<ActionIndexBean> searchActionPlan) {
        this.searchActionPlan = searchActionPlan;
    }

    @Keep
    public class ActionIndexBean implements Serializable{
        /**
         * task_id : 162461975040638714
         * task_title : 行动方案测试
         * task_priority : 2
         * task_type : S
         * task_deadline : 2021-06-30
         * task_state : 7
         * create_time : 2021-06-25 19:15:50
         * create_user : windy
         * create_user_name : 测试-舒婷
         * user_type : 1
         * user_pkid : 456463890261305951361
         * user_id : windy
         * task_text : [task]测试
         * state_desc : 完成
         * create_user_pkid : 456463890261305951361
         */

        private String task_id;
        private String task_title;
        private Integer task_priority;
        private String task_type;
        private String task_deadline;
        private String task_state;
        private String create_time;
        private String create_user;
        private String create_user_name;
        private String user_type;
        private String user_pkid;
        private String user_id;
        private String task_text;
        private String state_desc;
        private String create_user_pkid;

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getTask_title() {
            return task_title;
        }

        public void setTask_title(String task_title) {
            this.task_title = task_title;
        }

        public Integer getTask_priority() {
            return task_priority;
        }

        public void setTask_priority(Integer task_priority) {
            this.task_priority = task_priority;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        public String getTask_deadline() {
            return task_deadline;
        }

        public void setTask_deadline(String task_deadline) {
            this.task_deadline = task_deadline;
        }

        public String getTask_state() {
            return task_state;
        }

        public void setTask_state(String task_state) {
            this.task_state = task_state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public String getCreate_user_name() {
            return create_user_name;
        }

        public void setCreate_user_name(String create_user_name) {
            this.create_user_name = create_user_name;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_pkid() {
            return user_pkid;
        }

        public void setUser_pkid(String user_pkid) {
            this.user_pkid = user_pkid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTask_text() {
            return task_text;
        }

        public void setTask_text(String task_text) {
            this.task_text = task_text;
        }

        public String getState_desc() {
            return state_desc;
        }

        public void setState_desc(String state_desc) {
            this.state_desc = state_desc;
        }

        public String getCreate_user_pkid() {
            return create_user_pkid;
        }

        public void setCreate_user_pkid(String create_user_pkid) {
            this.create_user_pkid = create_user_pkid;
        }
    }
}
