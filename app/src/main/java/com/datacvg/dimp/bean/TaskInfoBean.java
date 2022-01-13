package com.datacvg.dimp.bean;

import androidx.annotation.Keep;
import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-18
 * @Description :
 */
@Keep
public class TaskInfoBean implements Serializable {

    /**
     * baseInfo : {"create_time":"2020-12-18 10:17:24","create_user_name":"测试账号","task_state":"11","task_id":"160819899626671387","commit_time":"2020-12-18 10:17:25","update_user_name":"测试账号","task_text":"测试安卓下发","update_time":"2020-12-18 10:17:25","update_user":"barry.chen","task_priority":1,"task_deadline":"2020-12-17","state_desc":"超期未完成","task_title":"测试安卓下发","create_user":"barry.chen","task_type":"N"}
     * handle : [{"id":"do_edit","desc":"编辑"},{"id":"do_cancel","desc":"撤销"}]
     * indexList : []
     * detail : [{"task_text_type":"1","user_name":"测试账号","task_text_state":"","change":[],"detail_pkid":"160819899629658734","task_state":"3","task_id":"160819899626671387","detail_id":"160819899629658734","task_text":"测试安卓下发","task_text_user":"barry.chen","task_text_order":"","update_time":"2020-12-17 17:56:36","update_user":"barry.chen","task_text_time":"2020-12-17 17:56:36","state_desc":"处理中","task_change":"","cur_state":"11"},{"task_text_type":"1","user_name":"测试账号","task_text_state":"","change":[],"detail_pkid":"160825784500381883","task_state":"10","task_id":"160819899626671387","detail_id":"160825784500381883","task_text":"测试安卓下发","task_text_user":"barry.chen","task_text_order":"","update_time":"2020-12-18 10:17:25","update_user":"barry.chen","task_text_time":"2020-12-18 10:17:25","state_desc":"编辑","task_change":"","cur_state":"11"}]
     * fastphotoold : []
     * plan : {"last_updater":"barry.chen","reason":"","edit_button":"false","submit_button":"false","user_type":"1","agree_button":"false","refuse_button":"false","plan_detail_list":[],"plan_id":"160819899632959841","plan_flg":"T","plan_status":"2"}
     * taskUser : [{"user_pkid":"barry.chen","creat_user_name":"测试账号","user_orgid":"14860367656855969470","create_user_orgid":"14860367656855969470","user_id":"barry.chen","creat_user":"barry.chen","name":"测试账号","type":"1","create_user_pkid":"149492828209879757"},{"user_pkid":"datacvg123","fzr_user_name":"datacvg123","user_orgid":"334393557152614928820","user_id":"datacvg123","fzr_user":"datacvg123","name":"datacvg123","fzr_user_pkid":"datacvg123","type":"2","fzr_user_orgid":"334393557152614928820"},{"user_pkid":"testsp","user_orgid":"941124792709227284900","user_id":"testsp","name":"testsp","type":"3"}]
     */

    private BaseInfoBean baseInfo;
    private PlanBean plan;
    private List<HandleBean> handle;
    private List<IndexListBean> indexList;
    private List<DetailBean> detail;
    private List<FastPhotoOldBean> fastphotoold;
    private List<TaskUserBean> taskUser;

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public PlanBean getPlan() {
        return plan;
    }

    public void setPlan(PlanBean plan) {
        this.plan = plan;
    }

    public List<HandleBean> getHandle() {
        return handle;
    }

    public void setHandle(List<HandleBean> handle) {
        this.handle = handle;
    }

    public List<IndexListBean> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<IndexListBean> indexList) {
        this.indexList = indexList;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public List<FastPhotoOldBean> getFastphotoold() {
        return fastphotoold;
    }

    public void setFastphotoold(List<FastPhotoOldBean> fastphotoold) {
        this.fastphotoold = fastphotoold;
    }

    public List<TaskUserBean> getTaskUser() {
        return taskUser;
    }

    public void setTaskUser(List<TaskUserBean> taskUser) {
        this.taskUser = taskUser;
    }

    @Keep
    public static class BaseInfoBean implements Serializable{
        /**
         * create_time : 2020-12-18 10:17:24
         * create_user_name : 测试账号
         * task_state : 11
         * task_id : 160819899626671387
         * commit_time : 2020-12-18 10:17:25
         * update_user_name : 测试账号
         * task_text : 测试安卓下发
         * update_time : 2020-12-18 10:17:25
         * update_user : barry.chen
         * task_priority : 1
         * task_deadline : 2020-12-17
         * state_desc : 超期未完成
         * task_title : 测试安卓下发
         * create_user : barry.chen
         * task_type : N
         */

        private String create_time;
        private String create_user_name;
        private String task_state;
        private String task_id;
        private String commit_time;
        private String update_user_name;
        private String task_text;
        private String update_time;
        private String update_user;
        private Integer task_priority;
        private String task_deadline;
        private String state_desc;
        private String task_title;
        private String create_user;

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

        public String getTask_state() {
            return task_state;
        }

        public void setTask_state(String task_state) {
            this.task_state = task_state;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getCommit_time() {
            return commit_time;
        }

        public void setCommit_time(String commit_time) {
            this.commit_time = commit_time;
        }

        public String getUpdate_user_name() {
            return update_user_name;
        }

        public void setUpdate_user_name(String update_user_name) {
            this.update_user_name = update_user_name;
        }

        public String getTask_text() {
            return task_text;
        }

        public void setTask_text(String task_text) {
            this.task_text = task_text;
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

        public Integer getTask_priority() {
            return task_priority;
        }

        public void setTask_priority(Integer task_priority) {
            this.task_priority = task_priority;
        }

        public String getTask_deadline() {
            return task_deadline;
        }

        public void setTask_deadline(String task_deadline) {
            this.task_deadline = task_deadline;
        }

        public String getState_desc() {
            return state_desc;
        }

        public void setState_desc(String state_desc) {
            this.state_desc = state_desc;
        }

        public String getTask_title() {
            return task_title;
        }

        public void setTask_title(String task_title) {
            this.task_title = task_title;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        private String task_type;
    }

    @Keep
    public static class PlanBean implements Serializable{
        /**
         * last_updater : barry.chen
         * reason :
         * edit_button : false
         * submit_button : false
         * user_type : 1
         * agree_button : false
         * refuse_button : false
         * plan_detail_list : []
         * plan_id : 160819899632959841
         * plan_flg : T
         * plan_status : 2
         */

        private String last_updater;
        private String reason;
        private String edit_button;
        private String submit_button;
        private String user_type;
        private String agree_button;
        private String refuse_button;
        private String plan_id;
        private String plan_flg;
        private String plan_status;
        private List<PlanDetailBean> plan_detail_list;

        public String getLast_updater() {
            return last_updater;
        }

        public void setLast_updater(String last_updater) {
            this.last_updater = last_updater;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getEdit_button() {
            return edit_button;
        }

        public void setEdit_button(String edit_button) {
            this.edit_button = edit_button;
        }

        public String getSubmit_button() {
            return submit_button;
        }

        public void setSubmit_button(String submit_button) {
            this.submit_button = submit_button;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getAgree_button() {
            return agree_button;
        }

        public void setAgree_button(String agree_button) {
            this.agree_button = agree_button;
        }

        public String getRefuse_button() {
            return refuse_button;
        }

        public void setRefuse_button(String refuse_button) {
            this.refuse_button = refuse_button;
        }

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getPlan_flg() {
            return plan_flg;
        }

        public void setPlan_flg(String plan_flg) {
            this.plan_flg = plan_flg;
        }

        public String getPlan_status() {
            return plan_status;
        }

        public void setPlan_status(String plan_status) {
            this.plan_status = plan_status;
        }

        public List<PlanDetailBean> getPlan_detail_list() {
            return plan_detail_list;
        }

        public void setPlan_detail_list(List<PlanDetailBean> plan_detail_list) {
            this.plan_detail_list = plan_detail_list;
        }

        @Keep
        public static class PlanDetailBean implements Serializable{

            /**
             * end_date : 2020-12-10
             * relation_planid_index : 160748491024190253
             * plan_title : 计划1
             * plan : 1111
             * plan_id : 160732483315656350
             * start_date : 2020-12-09
             */

            private String end_date;
            private String relation_planid_index;
            private String plan_title;
            private String plan;
            private String plan_id;
            private String start_date;

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getRelation_planid_index() {
                return relation_planid_index;
            }

            public void setRelation_planid_index(String relation_planid_index) {
                this.relation_planid_index = relation_planid_index;
            }

            public String getPlan_title() {
                return plan_title;
            }

            public void setPlan_title(String plan_title) {
                this.plan_title = plan_title;
            }

            public String getPlan() {
                return plan;
            }

            public void setPlan(String plan) {
                this.plan = plan;
            }

            public String getPlan_id() {
                return plan_id;
            }

            public void setPlan_id(String plan_id) {
                this.plan_id = plan_id;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }
        }
    }

    @Keep
    public static class HandleBean implements Serializable{
        /**
         * id : do_edit
         * desc : 编辑
         */

        private String id;
        private String desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    @Keep
    public static class DetailBean implements Serializable{
        /**
         * task_text_type : 1
         * user_name : 测试账号
         * task_text_state :
         * change : []
         * detail_pkid : 160819899629658734
         * task_state : 3
         * task_id : 160819899626671387
         * detail_id : 160819899629658734
         * task_text : 测试安卓下发
         * task_text_user : barry.chen
         * task_text_order :
         * update_time : 2020-12-17 17:56:36
         * update_user : barry.chen
         * task_text_time : 2020-12-17 17:56:36
         * state_desc : 处理中
         * task_change :
         * cur_state : 11
         */

        private String task_text_type;
        private String user_name;
        private String task_text_state;
        private String detail_pkid;
        private String task_state;
        private String task_id;
        private String detail_id;
        private String task_text;
        private String task_text_user;
        private String task_text_order;
        private String update_time;
        private String update_user;
        private String task_text_time;
        private String state_desc;
        private String task_change;
        private String cur_state;
        private List<ChangeBean> change;

        public String getTask_text_type() {
            return task_text_type;
        }

        public void setTask_text_type(String task_text_type) {
            this.task_text_type = task_text_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getTask_text_state() {
            return task_text_state;
        }

        public void setTask_text_state(String task_text_state) {
            this.task_text_state = task_text_state;
        }

        public String getDetail_pkid() {
            return detail_pkid;
        }

        public void setDetail_pkid(String detail_pkid) {
            this.detail_pkid = detail_pkid;
        }

        public String getTask_state() {
            return task_state;
        }

        public void setTask_state(String task_state) {
            this.task_state = task_state;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getDetail_id() {
            return detail_id;
        }

        public void setDetail_id(String detail_id) {
            this.detail_id = detail_id;
        }

        public String getTask_text() {
            return task_text;
        }

        public void setTask_text(String task_text) {
            this.task_text = task_text;
        }

        public String getTask_text_user() {
            return task_text_user;
        }

        public void setTask_text_user(String task_text_user) {
            this.task_text_user = task_text_user;
        }

        public String getTask_text_order() {
            return task_text_order;
        }

        public void setTask_text_order(String task_text_order) {
            this.task_text_order = task_text_order;
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

        public String getTask_text_time() {
            return task_text_time;
        }

        public void setTask_text_time(String task_text_time) {
            this.task_text_time = task_text_time;
        }

        public String getState_desc() {
            return state_desc;
        }

        public void setState_desc(String state_desc) {
            this.state_desc = state_desc;
        }

        public String getTask_change() {
            return task_change;
        }

        public void setTask_change(String task_change) {
            this.task_change = task_change;
        }

        public String getCur_state() {
            return cur_state;
        }

        public void setCur_state(String cur_state) {
            this.cur_state = cur_state;
        }

        public List<ChangeBean> getChange() {
            return change;
        }

        public void setChange(List<ChangeBean> change) {
            this.change = change;
        }

        @Keep
        public static class ChangeBean implements Serializable{

            /**
             * before : shuting
             * key_desc : 协助人
             * after : shuting, 879
             * key : user_xzr
             */

            private String before;
            private String key_desc;
            private String after;
            private String key;

            public String getBefore() {
                return before;
            }

            public void setBefore(String before) {
                this.before = before;
            }

            public String getKey_desc() {
                return key_desc;
            }

            public void setKey_desc(String key_desc) {
                this.key_desc = key_desc;
            }

            public String getAfter() {
                return after;
            }

            public void setAfter(String after) {
                this.after = after;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }

    @Keep
    public static class TaskUserBean implements Serializable{
        /**
         * user_pkid : barry.chen
         * creat_user_name : 测试账号
         * user_orgid : 14860367656855969470
         * create_user_orgid : 14860367656855969470
         * user_id : barry.chen
         * creat_user : barry.chen
         * name : 测试账号
         * type : 1
         * create_user_pkid : 149492828209879757
         * fzr_user_name : datacvg123
         * fzr_user : datacvg123
         * fzr_user_pkid : datacvg123
         * fzr_user_orgid : 334393557152614928820
         */

        private String user_pkid;
        private String creat_user_name;
        private String user_orgid;
        private String create_user_orgid;
        private String user_id;
        private String creat_user;
        private String name;
        private String type;
        private String create_user_pkid;
        private String fzr_user_name;
        private String fzr_user;
        private String fzr_user_pkid;
        private String fzr_user_orgid;

        public String getUser_pkid() {
            return user_pkid;
        }

        public void setUser_pkid(String user_pkid) {
            this.user_pkid = user_pkid;
        }

        public String getCreat_user_name() {
            return creat_user_name;
        }

        public void setCreat_user_name(String creat_user_name) {
            this.creat_user_name = creat_user_name;
        }

        public String getUser_orgid() {
            return user_orgid;
        }

        public void setUser_orgid(String user_orgid) {
            this.user_orgid = user_orgid;
        }

        public String getCreate_user_orgid() {
            return create_user_orgid;
        }

        public void setCreate_user_orgid(String create_user_orgid) {
            this.create_user_orgid = create_user_orgid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCreat_user() {
            return creat_user;
        }

        public void setCreat_user(String creat_user) {
            this.creat_user = creat_user;
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

        public String getCreate_user_pkid() {
            return create_user_pkid;
        }

        public void setCreate_user_pkid(String create_user_pkid) {
            this.create_user_pkid = create_user_pkid;
        }

        public String getFzr_user_name() {
            return fzr_user_name;
        }

        public void setFzr_user_name(String fzr_user_name) {
            this.fzr_user_name = fzr_user_name;
        }

        public String getFzr_user() {
            return fzr_user;
        }

        public void setFzr_user(String fzr_user) {
            this.fzr_user = fzr_user;
        }

        public String getFzr_user_pkid() {
            return fzr_user_pkid;
        }

        public void setFzr_user_pkid(String fzr_user_pkid) {
            this.fzr_user_pkid = fzr_user_pkid;
        }

        public String getFzr_user_orgid() {
            return fzr_user_orgid;
        }

        public void setFzr_user_orgid(String fzr_user_orgid) {
            this.fzr_user_orgid = fzr_user_orgid;
        }
    }

    @Keep
    public static class IndexListBean implements Serializable{

        /**
         * res_id : 85054292732340667634
         * index_id : 16775144543253257763
         * index_name : 项目成本
         * spare_name : Project cost allowence
         */

        private String res_id;
        private String index_id;
        private String index_name;
        private String spare_name;

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getIndex_name() {
            return index_name;
        }

        public void setIndex_name(String index_name) {
            this.index_name = index_name;
        }

        public String getSpare_name() {
            return spare_name;
        }

        public void setSpare_name(String spare_name) {
            this.spare_name = spare_name;
        }
    }

    @Keep
    public static class FastPhotoOldBean implements Serializable{

        /**
         * index_clname : 项目成本
         * index_root_id :
         * relation_i_id : 85054292732340667634
         * orgdimension : 14860367656855969470
         * action_type : 4 sand
         * alldeimension : 数聚股份,所有地区,实施与服务
         * relation_i_pkid : 1254091048124535621024
         * task_id : 160732483302825516
         * pdimension : 118343610455720651903
         * update_time : 202012
         * dimention_preid :
         * update_user : gaozongwei
         * action_time : 202008
         * demention_info : []
         * time : 202012
         * index_id : 16775144543253257763
         * fudimension : 118306192070461277956
         * newval : []
         * save_data : [{"index_data":"15.47","value_unit":"万元","value_description":"实际"}]
         */

        private String index_clname;
        private String index_root_id;
        private String relation_i_id;
        private String orgdimension;
        private String action_type;
        private String alldeimension;
        private String relation_i_pkid;
        private String task_id;
        private String pdimension;
        private String update_time;
        private String dimention_preid;
        private String update_user;
        private String action_time;
        private String time;
        private String index_id;
        private String tree_type;
        private String index_unit;
        private String fast_code ;
        private String no_permission_name ;
        private String deadline ;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getFast_code() {
            return fast_code;
        }

        public void setFast_code(String fast_code) {
            this.fast_code = fast_code;
        }

        public String getNo_permission_name() {
            return no_permission_name;
        }

        public void setNo_permission_name(String no_permission_name) {
            this.no_permission_name = no_permission_name;
        }

        public String getIndex_unit() {
            return index_unit;
        }

        public void setIndex_unit(String index_unit) {
            this.index_unit = index_unit;
        }

        private String fudimension;
        private String save_data;
        private List<DementionInfoBean> demention_info;
        private String newval;

        public String getTree_type() {
            return tree_type;
        }

        public void setTree_type(String tree_type) {
            this.tree_type = tree_type;
        }

        public void setNewval(String newval) {
            this.newval = newval;
        }

        public String getIndex_clname() {
            return index_clname;
        }

        public void setIndex_clname(String index_clname) {
            this.index_clname = index_clname;
        }

        public String getIndex_root_id() {
            return index_root_id;
        }

        public void setIndex_root_id(String index_root_id) {
            this.index_root_id = index_root_id;
        }

        public String getRelation_i_id() {
            return relation_i_id;
        }

        public void setRelation_i_id(String relation_i_id) {
            this.relation_i_id = relation_i_id;
        }

        public String getOrgdimension() {
            return orgdimension;
        }

        public void setOrgdimension(String orgdimension) {
            this.orgdimension = orgdimension;
        }

        public String getAction_type() {
            return action_type;
        }

        public void setAction_type(String action_type) {
            this.action_type = action_type;
        }

        public String getAlldeimension() {
            return alldeimension;
        }

        public void setAlldeimension(String alldeimension) {
            this.alldeimension = alldeimension;
        }

        public String getRelation_i_pkid() {
            return relation_i_pkid;
        }

        public void setRelation_i_pkid(String relation_i_pkid) {
            this.relation_i_pkid = relation_i_pkid;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getPdimension() {
            return pdimension;
        }

        public void setPdimension(String pdimension) {
            this.pdimension = pdimension;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getDimention_preid() {
            return dimention_preid;
        }

        public void setDimention_preid(String dimention_preid) {
            this.dimention_preid = dimention_preid;
        }

        public String getUpdate_user() {
            return update_user;
        }

        public void setUpdate_user(String update_user) {
            this.update_user = update_user;
        }

        public String getAction_time() {
            return action_time;
        }

        public void setAction_time(String action_time) {
            this.action_time = action_time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getFudimension() {
            return fudimension;
        }

        public void setFudimension(String fudimension) {
            this.fudimension = fudimension;
        }

        public String getSave_data() {
            return save_data;
        }

        public void setSave_data(String save_data) {
            this.save_data = save_data;
        }

        public List<DementionInfoBean> getDemention_info() {
            return demention_info;
        }

        public void setDemention_info(List<DementionInfoBean> demention_info) {
            this.demention_info = demention_info;
        }

        public String getNewval() {
            return newval;
        }

        @Keep
        public static class DementionInfoBean implements Serializable{

            /**
             * index_value_unit : 万元
             * index_threshold_type : 1
             * index_value_type_fmt : ###,###.##
             * threshold_value : 162.36
             * chart_unit : 元
             * type : 2
             * index_mnt_description : 万
             * index_value_mnt : 10000
             * index_threshold_value :
             */

            private String index_value_unit;
            private String index_threshold_type;
            private String index_value_type_fmt;
            private String threshold_value;
            private String chart_unit;
            private String type;
            private String index_mnt_description;
            private String index_value_mnt;
            private String index_threshold_value;

            public String getIndex_value_unit() {
                return index_value_unit;
            }

            public void setIndex_value_unit(String index_value_unit) {
                this.index_value_unit = index_value_unit;
            }

            public String getIndex_threshold_type() {
                return index_threshold_type;
            }

            public void setIndex_threshold_type(String index_threshold_type) {
                this.index_threshold_type = index_threshold_type;
            }

            public String getIndex_value_type_fmt() {
                return index_value_type_fmt;
            }

            public void setIndex_value_type_fmt(String index_value_type_fmt) {
                this.index_value_type_fmt = index_value_type_fmt;
            }

            public String getThreshold_value() {
                return threshold_value;
            }

            public void setThreshold_value(String threshold_value) {
                this.threshold_value = threshold_value;
            }

            public String getChart_unit() {
                return chart_unit;
            }

            public void setChart_unit(String chart_unit) {
                this.chart_unit = chart_unit;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIndex_mnt_description() {
                return index_mnt_description;
            }

            public void setIndex_mnt_description(String index_mnt_description) {
                this.index_mnt_description = index_mnt_description;
            }

            public String getIndex_value_mnt() {
                return index_value_mnt;
            }

            public void setIndex_value_mnt(String index_value_mnt) {
                this.index_value_mnt = index_value_mnt;
            }

            public String getIndex_threshold_value() {
                return index_threshold_value;
            }

            public void setIndex_threshold_value(String index_threshold_value) {
                this.index_threshold_value = index_threshold_value;
            }
        }

        @Keep
        private class NewvalBean implements Serializable{

            /**
             * index_id : IBI-income
             * time_val : 202011
             * value : -192.08
             * value_type : ddb1
             * value_unit : 万元
             * value_mnt : 10000
             * value_fmt : ###,###.##
             * mnt_description : 万
             * chart_unit : 元
             * source_value : -1920773.4227
             * source_value_unit :
             */

            private String index_id;
            private Integer time_val;
            private String value;
            private String value_type;
            private String value_unit;
            private String value_mnt;
            private String value_fmt;
            private String mnt_description;
            private String chart_unit;
            private Double source_value;
            private String source_value_unit;

            public String getIndex_id() {
                return index_id;
            }

            public void setIndex_id(String index_id) {
                this.index_id = index_id;
            }

            public Integer getTime_val() {
                return time_val;
            }

            public void setTime_val(Integer time_val) {
                this.time_val = time_val;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getValue_type() {
                return value_type;
            }

            public void setValue_type(String value_type) {
                this.value_type = value_type;
            }

            public String getValue_unit() {
                return value_unit;
            }

            public void setValue_unit(String value_unit) {
                this.value_unit = value_unit;
            }

            public String getValue_mnt() {
                return value_mnt;
            }

            public void setValue_mnt(String value_mnt) {
                this.value_mnt = value_mnt;
            }

            public String getValue_fmt() {
                return value_fmt;
            }

            public void setValue_fmt(String value_fmt) {
                this.value_fmt = value_fmt;
            }

            public String getMnt_description() {
                return mnt_description;
            }

            public void setMnt_description(String mnt_description) {
                this.mnt_description = mnt_description;
            }

            public String getChart_unit() {
                return chart_unit;
            }

            public void setChart_unit(String chart_unit) {
                this.chart_unit = chart_unit;
            }

            public Double getSource_value() {
                return source_value;
            }

            public void setSource_value(Double source_value) {
                this.source_value = source_value;
            }

            public String getSource_value_unit() {
                return source_value_unit;
            }

            public void setSource_value_unit(String source_value_unit) {
                this.source_value_unit = source_value_unit;
            }
        }

        @Keep
        public static class SaveDataBean implements Serializable{

            /**
             * index_data : 769,343.66
             * value_unit : 元
             * value_description : 实际
             */

            private String index_data;
            private String value_unit;
            private String value_description;
            private String goal_value ;

            public String getGoal_value() {
                return goal_value;
            }

            public void setGoal_value(String goal_value) {
                this.goal_value = goal_value;
            }

            public String getIndex_data() {
                return index_data;
            }

            public void setIndex_data(String index_data) {
                this.index_data = index_data;
            }

            public String getValue_unit() {
                return value_unit;
            }

            public void setValue_unit(String value_unit) {
                this.value_unit = value_unit;
            }

            public String getValue_description() {
                return value_description;
            }

            public void setValue_description(String value_description) {
                this.value_description = value_description;
            }
        }
    }
}
