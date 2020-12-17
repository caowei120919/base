package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-07
 * @Description : 行动计划创建修改封装bean
 */
@Keep
public class CreateTaskBean {

    private ActionPlanInfoDTO actionPlanInfoDTO ;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public CreateTaskBean() {

    }

    //创建时间
    private String actionType ;
    private String index;
    private String jumpFlg = "F";
    private String lang ;
    private String planFlg = "F";
    private String taskText ;
    private String userMsg ;
    private String indexList ;

    public String getAllDeimension() {
        return allDeimension;
    }

    public void setAllDeimension(String allDeimension) {
        this.allDeimension = allDeimension;
    }

    public String getFuDimension() {
        return fuDimension;
    }

    public void setFuDimension(String fuDimension) {
        this.fuDimension = fuDimension;
    }

    public String getOrgDimension() {
        return orgDimension;
    }

    public void setOrgDimension(String orgDimension) {
        this.orgDimension = orgDimension;
    }

    public String getpDimension() {
        return pDimension;
    }

    public void setpDimension(String pDimension) {
        this.pDimension = pDimension;
    }

    private String allDeimension ;
    private String fuDimension ;
    private String orgDimension ;
    private String pDimension ;

    public String getIndexList() {
        return indexList;
    }

    public void setIndexList(String indexList) {
        this.indexList = indexList;
    }

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

    public ActionPlanInfoDTO getActionPlanInfoDTO() {
        return actionPlanInfoDTO;
    }

    public void setActionPlanInfoDTO(ActionPlanInfoDTO actionPlanInfoDTO) {
        this.actionPlanInfoDTO = actionPlanInfoDTO;
    }

    @Keep
    public static class ActionPlanInfoDTO {
        private String task_deadline ;
        private String task_id = "";
        private String task_parent_id ;
        private String task_pkid ="";
        private String task_priority ;
        private String task_state = "";
        private String task_title ;
        private String task_type = "N";
        private String update_time = "";
        private String update_user = "";

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
    }
}
