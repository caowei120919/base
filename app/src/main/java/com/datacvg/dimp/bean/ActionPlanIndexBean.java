package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-03
 * @Description :
 */
@Keep
public class ActionPlanIndexBean implements Serializable {
    /**
     * relation_i_id : 1874210758208575801734
     * rootid : 83967606548302484211
     * name : 运维收入
     * checked : false
     * pid : 83967606548302484211
     * id : 1874210758208575801734
     * index_id : 15341079964696855071
     */

    private String relation_i_id;
    private String rootid;

    public String getRelation_i_id() {
        return relation_i_id;
    }

    public void setRelation_i_id(String relation_i_id) {
        this.relation_i_id = relation_i_id;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    private String name;
    private boolean checked;
    private String pid;
    private String id;
    private String index_id;
}
