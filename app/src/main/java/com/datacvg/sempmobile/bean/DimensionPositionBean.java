package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 指标实体
 */
@Keep
public class DimensionPositionBean {

    /**
     * index_pkid : 15357929555593893954
     * positon_pkid : 586333237353892731325
     * size_y : 1
     * size_x : 1
     * pos_y : 5
     * update_time : 2020-10-12 15:00:29
     * pos_x : 1
     * update_user : 149492828209879757
     * index_description :
     * chart_type : text
     * role_pkid :
     * index_id : IBI-inc_per_head
     * analysis_dimension : TIME
     */

    private String index_pkid;
    private String positon_pkid;
    private int size_y;
    private int size_x;
    private int pos_y;
    private String update_time;
    private int pos_x;
    private String update_user;
    private String index_description;
    private String chart_type;
    private String role_pkid;
    private String index_id;
    private String analysis_dimension;

    public String getIndex_pkid() {
        return index_pkid;
    }

    public void setIndex_pkid(String index_pkid) {
        this.index_pkid = index_pkid;
    }

    public String getPositon_pkid() {
        return positon_pkid;
    }

    public void setPositon_pkid(String positon_pkid) {
        this.positon_pkid = positon_pkid;
    }

    public int getSize_y() {
        return size_y;
    }

    public void setSize_y(int size_y) {
        this.size_y = size_y;
    }

    public int getSize_x() {
        return size_x;
    }

    public void setSize_x(int size_x) {
        this.size_x = size_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getIndex_description() {
        return index_description;
    }

    public void setIndex_description(String index_description) {
        this.index_description = index_description;
    }

    public String getChart_type() {
        return chart_type;
    }

    public void setChart_type(String chart_type) {
        this.chart_type = chart_type;
    }

    public String getRole_pkid() {
        return role_pkid;
    }

    public void setRole_pkid(String role_pkid) {
        this.role_pkid = role_pkid;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public String getAnalysis_dimension() {
        return analysis_dimension;
    }

    public void setAnalysis_dimension(String analysis_dimension) {
        this.analysis_dimension = analysis_dimension;
    }
}
