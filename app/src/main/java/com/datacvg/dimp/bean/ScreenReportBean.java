package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.tree.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-03-24
 * @Description :
 */
@Keep
public class ScreenReportBean implements Serializable {

    /**
     * res_pkid : 2555294823256546335212
     * res_id : 2555294823255939428360
     * res_clname : 圆形
     * res_flname : 圆形
     * res_parentid : 2555283633109099562485
     * res_rootid : 111111111
     * classify : screen
     * imgpath :
     * update_time : 2022-03-09 16:13:52
     * res_showtype : model_report
     */

    private String res_pkid;
    private String res_id;
    private String res_clname;
    private String res_flname;
    private String res_parentid;
    private String res_rootid;
    private String classify;
    private String imgpath;
    private String update_time;
    private String res_showtype;
    private Integer level = 0 ;
    private Boolean isOpen = false ;
    private List<ScreenReportBean> child = new ArrayList<>() ;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public List<ScreenReportBean> getChild() {
        return child;
    }

    public void setChild(List<ScreenReportBean> child) {
        this.child = child;
    }

    public String getRes_pkid() {
        return res_pkid;
    }

    public void setRes_pkid(String res_pkid) {
        this.res_pkid = res_pkid;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
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

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getRes_showtype() {
        return res_showtype;
    }

    public void setRes_showtype(String res_showtype) {
        this.res_showtype = res_showtype;
    }
}
