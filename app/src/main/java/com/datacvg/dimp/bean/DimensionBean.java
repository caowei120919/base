package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description : 维度
 */
@Keep
public class DimensionBean {


    /**
     * id : 602219233859972617510
     * d_res_name : sf多维度1
     * pid : 0
     * d_res_rootid : 602219233859972617510
     * d_res_value : sftest1
     * defaultkey : sf1
     * d_res_stime : 20140401
     * d_res_etime : 20250430
     * res_level : 0
     * res_level_sort : 42
     * orgFlag :
     * nodes : [{"id":"654225027948360427988","d_res_name":"一环","pid":"602219233859972617510","d_res_rootid":"602219233859972617510","d_res_value":"sf-1","defaultkey":"sf1","d_res_stime":"20140401","d_res_etime":"20240430","res_level":1,"res_level_sort":0,"orgFlag":""},{"id":"654227083199379599861","d_res_name":"二环","pid":"602219233859972617510","d_res_rootid":"602219233859972617510","d_res_value":"sf-2","defaultkey":"sf1","d_res_stime":"20140401","d_res_etime":"20250430","res_level":1,"res_level_sort":1,"orgFlag":""},{"id":"810694478578483749220","d_res_name":"三环","pid":"602219233859972617510","d_res_rootid":"602219233859972617510","d_res_value":"sf-3","defaultkey":"sf1","d_res_stime":"20140401","d_res_etime":"20250430","res_level":1,"res_level_sort":2,"orgFlag":""}]
     */

    private String id;
    private String d_res_name;
    private String pid;
    private String d_res_rootid;
    private String d_res_value;
    private String defaultkey;
    private String d_res_stime;
    private String d_res_etime;
    private Integer res_level;
    private Integer res_level_sort;
    private String orgFlag;
    private List<DimensionBean> nodes;
    private Boolean isOpen = false ;

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getD_res_name() {
        return d_res_name;
    }

    public void setD_res_name(String d_res_name) {
        this.d_res_name = d_res_name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getD_res_rootid() {
        return d_res_rootid;
    }

    public void setD_res_rootid(String d_res_rootid) {
        this.d_res_rootid = d_res_rootid;
    }

    public String getD_res_value() {
        return d_res_value;
    }

    public void setD_res_value(String d_res_value) {
        this.d_res_value = d_res_value;
    }

    public String getDefaultkey() {
        return defaultkey;
    }

    public void setDefaultkey(String defaultkey) {
        this.defaultkey = defaultkey;
    }

    public String getD_res_stime() {
        return d_res_stime;
    }

    public void setD_res_stime(String d_res_stime) {
        this.d_res_stime = d_res_stime;
    }

    public String getD_res_etime() {
        return d_res_etime;
    }

    public void setD_res_etime(String d_res_etime) {
        this.d_res_etime = d_res_etime;
    }

    public Integer getRes_level() {
        return res_level;
    }

    public void setRes_level(Integer res_level) {
        this.res_level = res_level;
    }

    public Integer getRes_level_sort() {
        return res_level_sort;
    }

    public void setRes_level_sort(Integer res_level_sort) {
        this.res_level_sort = res_level_sort;
    }

    public String getOrgFlag() {
        return orgFlag;
    }

    public void setOrgFlag(String orgFlag) {
        this.orgFlag = orgFlag;
    }

    public List<DimensionBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<DimensionBean> nodes) {
        this.nodes = nodes;
    }
}
