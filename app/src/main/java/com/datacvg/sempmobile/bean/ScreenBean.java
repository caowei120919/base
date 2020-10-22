package com.datacvg.sempmobile.bean;

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
     * pkid : 415900908808246763024
     * screen_id : 415900908808246763024
     * screen_name : 大屏test
     * screen_format : {"type":"16:9","size":"32","width":"2000","height":"2000","direction":"horizontal"}
     * screen_attribute : {"animationMode":"circulation"}
     * img_path : image_read/415905614246558320559_mini.jpg
     * create_time : 2020-09-22 21:35:06
     * sort_val :
     * update_user : 1691179324035185017442
     * update_time : 2020-09-22 21:37:12
     * delete_flag : 1
     * login_name : shuting
     * edit_flag : 1
     * view_flag : 1
     */

    private String pkid;
    private String screen_id;
    private String screen_name;
    private String screen_format;
    private String screen_attribute;
    private String img_path;
    private String create_time;
    private String sort_val;
    private String update_user;
    private String update_time;
    private int delete_flag;
    private String login_name;
    private int edit_flag;
    private int view_flag;

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

    public int getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(int delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public int getEdit_flag() {
        return edit_flag;
    }

    public void setEdit_flag(int edit_flag) {
        this.edit_flag = edit_flag;
    }

    public int getView_flag() {
        return view_flag;
    }

    public void setView_flag(int view_flag) {
        this.view_flag = view_flag;
    }
}
