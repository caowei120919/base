package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-28
 * @Description :
 */
@Keep
public class ReportTrashBean implements Serializable {

    /**
     * classify : screen
     * pkid : 168994780925539301543
     * res_clname : 线下门店销售分析大屏
     * res_flname : 线下门店销售分析大屏
     * res_id : 168994780924449734109
     * res_parentid : 1000000000
     * res_rootid : 1000000000
     * res_type : model_report
     * thumbnail_path : 1146330294755352224491
     * update_time : 2021-09-28 11:13:01
     * update_user : 456463890261305951361
     * user_pkid : 456463890261305951361
     */

    private String classify;
    private String pkid;
    private String res_clname;
    private String res_flname;
    private String res_id;
    private String res_parentid;
    private String res_rootid;
    private String res_type;
    private String thumbnail_path;
    private String update_time;
    private String update_user;
    private String user_pkid;

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getRes_clname() {
        return res_clname;
    }

    public void setRes_clname(String res_clname) {
        this.res_clname = res_clname;
    }

    public String getRes_flname() {
        return res_flname;
    }

    public void setRes_flname(String res_flname) {
        this.res_flname = res_flname;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRes_parentid() {
        return res_parentid;
    }

    public void setRes_parentid(String res_parentid) {
        this.res_parentid = res_parentid;
    }

    public String getRes_rootid() {
        return res_rootid;
    }

    public void setRes_rootid(String res_rootid) {
        this.res_rootid = res_rootid;
    }

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
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

    public String getUser_pkid() {
        return user_pkid;
    }

    public void setUser_pkid(String user_pkid) {
        this.user_pkid = user_pkid;
    }
}
