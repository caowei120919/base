package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-07
 * @Description : 行动计划创建修改封装bean
 */
@Keep
public class ActionPlanInfoDTO {
    public String getTask_deadline() {
        return task_deadline;
    }

    public void setTask_deadline(String task_deadline) {
        this.task_deadline = task_deadline;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_parent_id() {
        return task_parent_id;
    }

    public void setTask_parent_id(String task_parent_id) {
        this.task_parent_id = task_parent_id;
    }

    public String getTask_pkid() {
        return task_pkid;
    }

    public List<ActionPlanIndexBean> getIndex() {
        return index;
    }

    public void setIndex(List<ActionPlanIndexBean> index) {
        this.index = index;
    }

    public void setTask_pkid(String task_pkid) {
        this.task_pkid = task_pkid;
    }

    public String getTask_priority() {
        return task_priority;
    }

    public void setTask_priority(String task_priority) {
        this.task_priority = task_priority;
    }

    public String getTask_state() {
        return task_state;
    }

    public void setTask_state(String task_state) {
        this.task_state = task_state;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getJumpFlg() {
        return jumpFlg;
    }

    public void setJumpFlg(String jumpFlg) {
        this.jumpFlg = jumpFlg;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPlanFlg() {
        return planFlg;
    }

    public void setPlanFlg(String planFlg) {
        this.planFlg = planFlg;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public List<TaskUser> getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(List<TaskUser> userMsg) {
        this.userMsg = userMsg;
    }

    public ActionPlanInfoDTO() {

    }

    //创建时间
    private String task_deadline ;
    private String task_id ;
    private String task_parent_id ;
    private String task_pkid ;
    private String task_priority ;
    private String task_state ;
    private String task_title ;
    private String task_type = "N";
    private String update_time ;
    private String update_user ;
    private String actionType ;
    private List<ActionPlanIndexBean> index;
    private String jumpFlg ;
    private String lang ;
    private String planFlg = "F";
    private String taskText ;
    private List<TaskUser> userMsg ;

    /**
     * 行动人员选择实体
     */
    @Keep
    public static class TaskUser {

        /**
         * checked : true
         * id : lw
         * name : lw
         * type : 2
         */

        private boolean checked;
        private String id;
        private String name;
        private String type;

        public TaskUser() {

        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
