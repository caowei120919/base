package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-21
 * @Description : 行动方案
 */
@Keep
public class ActionPlanBean {

    /**
     * create_time : 2020-10-16 15:27:51
     * create_user_name : 陈庆华
     * cnt : 95
     * index : []
     * title : 下发子任务
     * priority : 1
     * type : N
     * imgurl : /login/readheadimg?userPkid=149492828209879757
     * user_type : 1
     * state_desc : 超期未完成
     * id : 160077577242318723
     * text : 1、下发子任务
     * state : 11
     * create_user : barry.chen
     * deadline : 2020-10-08
     * user : [{"id":"barry.chen","type":"1"}]
     * create_user_pkid : 149492828209879757
     */

    private String create_time;
    private String create_user_name;
    private int cnt;
    private String title;
    private int priority;
    private String type;
    private String imgurl;
    private int user_type;
    private String state_desc;
    private String id;
    private String text;
    private int state;
    private String create_user;
    private String deadline;
    private String create_user_pkid;
    private List<?> index;
    private List<UserBean> user;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getState_desc() {
        return state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCreate_user_pkid() {
        return create_user_pkid;
    }

    public void setCreate_user_pkid(String create_user_pkid) {
        this.create_user_pkid = create_user_pkid;
    }

    public List<?> getIndex() {
        return index;
    }

    public void setIndex(List<?> index) {
        this.index = index;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : barry.chen
         * type : 1
         */

        private String id;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
