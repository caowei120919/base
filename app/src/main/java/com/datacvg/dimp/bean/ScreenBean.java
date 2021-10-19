package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-10
 * @Description : 大屏
 */
@Keep
public class ScreenBean implements Serializable {

    /**
     * pkid : 940103874207329786012
     * screen_id : 940103874207329786012
     * screen_name : 大屏123
     * screen_format : {"type":"16:9","size":"32","width":"2000","height":"2000","direction":"horizontal"}
     * screen_attribute : {"animationTime":300,"animationMode":"circulation","animationEffect":"moveLeft"}
     * screen_thumbnail :
     * img_path :
     * create_time : 2021-09-03 17:35:22
     * sort_val :
     * update_user : 456463890261305951361
     * update_time : 2021-09-03 17:35:22
     * delete_flag : 1
     * login_name : windy
     * edit_flag : 1
     * view_flag : 1
     */

    private String pkid;
    private String screen_id;
    private String screen_name;
    private String screen_format;
    private String screen_attribute;
    private String screen_thumbnail;
    private String img_path;
    private String create_time;
    private String sort_val;
    private String update_user;
    private String update_time;
    private Integer delete_flag;
    private String login_name;
    private Integer edit_flag;
    private Integer view_flag;

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getScreen_id() {
        return screen_id;
    }

    public void setScreen_id(String screen_id) {
        this.screen_id = screen_id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getScreen_format() {
        return screen_format;
    }

    public void setScreen_format(String screen_format) {
        this.screen_format = screen_format;
    }

    public String getScreen_attribute() {
        return screen_attribute;
    }

    public void setScreen_attribute(String screen_attribute) {
        this.screen_attribute = screen_attribute;
    }

    public String getScreen_thumbnail() {
        return screen_thumbnail;
    }

    public void setScreen_thumbnail(String screen_thumbnail) {
        this.screen_thumbnail = screen_thumbnail;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSort_val() {
        return sort_val;
    }

    public void setSort_val(String sort_val) {
        this.sort_val = sort_val;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Integer getEdit_flag() {
        return edit_flag;
    }

    public void setEdit_flag(Integer edit_flag) {
        this.edit_flag = edit_flag;
    }

    public Integer getView_flag() {
        return view_flag;
    }

    public void setView_flag(Integer view_flag) {
        this.view_flag = view_flag;
    }
}
