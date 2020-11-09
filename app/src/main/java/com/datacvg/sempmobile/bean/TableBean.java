package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 报表实体类
 */
@Keep
public class TableBean {

    /**
     * res_parentid : 0
     * res_level_sort : 4
     * res_belong_project :
     * res_stime :
     * res_etime :
     * res_cuid :
     * res_server :
     * if_fav : 0
     * res_pkid : 141776322849706849
     * update_time : 2020-05-12 14:48:55
     * res_clname : 主题报表
     * update_user : 10000000
     * model_screen :
     * res_sort : 0
     * res_mstr_type :
     * res_id : 141776322849763954
     * res_value :
     * res_rootid : 141776322849763954
     * res_selkeyword : N
     * res_level : 0
     * res_showtype : FOLDER
     * res_imgpath : app_readResImg?res_id=141776322849763954&res_parentid=0
     * res_description :
     * res_flname : Themed Reports
     * res_opentype :
     * res_url : /reportapp/#/reports
     * res_ptype :
     * res_systype : APP_SYS
     * res_docid :
     * status : T
     */

    private String res_parentid;
    private Integer res_level_sort;
    private String res_belong_project;
    private String res_stime;
    private String res_etime;
    private String res_cuid;
    private String res_server;
    private Integer if_fav;
    private String res_pkid;
    private String update_time;
    private String res_clname;
    private String update_user;
    private String model_screen;
    private String res_sort;
    private String res_mstr_type;
    private String res_id;
    private String res_value;
    private String res_rootid;
    private String res_selkeyword;
    private Integer res_level;
    /**
     * FOLDER 文件夹
     * 报表类型有CUSTOMJUMP  MODEL   CUSTOMRPT  powerbi   powerbi_install   TABLEAU   CX   BO_DASHBOARD
     */
    private String res_showtype;
    private String res_imgpath;
    private String res_description;
    private String res_flname;
    private String res_opentype;
    private String res_url;
    private String res_ptype;
    private String res_systype;
    private String res_docid;
    private String status;
    private byte[] decode ;

    public String getRes_parentid() {
        return res_parentid;
    }

    public void setRes_parentid(String res_parentid) {
        this.res_parentid = res_parentid;
    }

    public Integer getRes_level_sort() {
        return res_level_sort;
    }

    public void setRes_level_sort(Integer res_level_sort) {
        this.res_level_sort = res_level_sort;
    }

    public String getRes_belong_project() {
        return res_belong_project;
    }

    public void setRes_belong_project(String res_belong_project) {
        this.res_belong_project = res_belong_project;
    }

    public String getRes_stime() {
        return res_stime;
    }

    public void setRes_stime(String res_stime) {
        this.res_stime = res_stime;
    }

    public String getRes_etime() {
        return res_etime;
    }

    public void setRes_etime(String res_etime) {
        this.res_etime = res_etime;
    }

    public String getRes_cuid() {
        return res_cuid;
    }

    public void setRes_cuid(String res_cuid) {
        this.res_cuid = res_cuid;
    }

    public String getRes_server() {
        return res_server;
    }

    public void setRes_server(String res_server) {
        this.res_server = res_server;
    }

    public Integer getIf_fav() {
        return if_fav;
    }

    public void setIf_fav(Integer if_fav) {
        this.if_fav = if_fav;
    }

    public String getRes_pkid() {
        return res_pkid;
    }

    public void setRes_pkid(String res_pkid) {
        this.res_pkid = res_pkid;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getRes_clname() {
        return res_clname;
    }

    public void setRes_clname(String res_clname) {
        this.res_clname = res_clname;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getModel_screen() {
        return model_screen;
    }

    public void setModel_screen(String model_screen) {
        this.model_screen = model_screen;
    }

    public String getRes_sort() {
        return res_sort;
    }

    public void setRes_sort(String res_sort) {
        this.res_sort = res_sort;
    }

    public String getRes_mstr_type() {
        return res_mstr_type;
    }

    public void setRes_mstr_type(String res_mstr_type) {
        this.res_mstr_type = res_mstr_type;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRes_value() {
        return res_value;
    }

    public void setRes_value(String res_value) {
        this.res_value = res_value;
    }

    public String getRes_rootid() {
        return res_rootid;
    }

    public void setRes_rootid(String res_rootid) {
        this.res_rootid = res_rootid;
    }

    public String getRes_selkeyword() {
        return res_selkeyword;
    }

    public void setRes_selkeyword(String res_selkeyword) {
        this.res_selkeyword = res_selkeyword;
    }

    public Integer getRes_level() {
        return res_level;
    }

    public void setRes_level(Integer res_level) {
        this.res_level = res_level;
    }

    public String getRes_showtype() {
        return res_showtype;
    }

    public void setRes_showtype(String res_showtype) {
        this.res_showtype = res_showtype;
    }

    public String getRes_imgpath() {
        return res_imgpath;
    }

    public void setRes_imgpath(String res_imgpath) {
        this.res_imgpath = res_imgpath;
    }

    public String getRes_description() {
        return res_description;
    }

    public void setRes_description(String res_description) {
        this.res_description = res_description;
    }

    public String getRes_flname() {
        return res_flname;
    }

    public void setRes_flname(String res_flname) {
        this.res_flname = res_flname;
    }

    public String getRes_opentype() {
        return res_opentype;
    }

    public void setRes_opentype(String res_opentype) {
        this.res_opentype = res_opentype;
    }

    public String getRes_url() {
        return res_url;
    }

    public void setRes_url(String res_url) {
        this.res_url = res_url;
    }

    public String getRes_ptype() {
        return res_ptype;
    }

    public void setRes_ptype(String res_ptype) {
        this.res_ptype = res_ptype;
    }

    public String getRes_systype() {
        return res_systype;
    }

    public void setRes_systype(String res_systype) {
        this.res_systype = res_systype;
    }

    public String getRes_docid() {
        return res_docid;
    }

    public void setRes_docid(String res_docid) {
        this.res_docid = res_docid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImageRes(byte[] decode) {
        this.decode = decode ;
    }

    public byte[] getDecode() {
        return decode;
    }
}
