package com.datacvg.dimp.bean;

import android.text.TextUtils;

import androidx.annotation.Keep;
import com.datacvg.dimp.baseandroid.config.Constants;
import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-03
 * @Description : 报告实体对象
 */
@Keep
public class ReportBean implements Serializable {

    /**
     * pkid : 507195342673122960444
     * model_id : 507195342671760185705
     * model_clname : 其他控件百分比换算问题
     * model_flname : 其他控件百分比换算问题
     * model_type : model_report
     * parent_id : 1000000000
     * root_id : 1000000000
     * thumbnail_path :
     * update_user : 456463890261305951361
     * update_time : 2021-07-19 15:25:30
     * classify :
     */

    /**
     * "pkid": "855975973892311026124",
     * "share_id": "855975970134442152212",
     * "share_clname": "测试",
     * "share_flname": "测试",
     * "share_showtype": "share_folder",
     * "share_parentid": "100000000",
     * "share_rootid": "100000000",
     * "update_user": "456463890261305951361",
     * "update_time": "2021-05-15 19:41:34",
     * "res_level": "",
     * "level_sort": "",
     * "status": "T",
     * "thumbnail_path": "",
     * "classify": "",
     * "sign": "p"
     */

    /**
     * "template_id":"10000000001",
     *  "template_clname":"布局模板",
     *  "template_flname":"Layout Folders",
     *  "template_showtype":"template_folder",
     *  "template_parentid":"10000000000",
     *      * 			"template_rootid":"10000000000",
     */

    private String pkid;
    private String share_id;
    private String share_clname;
    private String share_flname;
    private String share_showtype = "";
    private String share_parentid;
    private String share_rootid;

    private String template_id ;
    private String template_clname ;
    private String template_flname ;
    private String template_showtype = "";
    private String template_parentid ;
    private String template_rootid ;

    private String update_user;
    private String update_time;
    private String res_level;
    private String level_sort;
    private String status;
    private String combination_id;
    private String classify = "";
    private String sign = "p";
    private String model_id;
    private String model_clname;
    private String model_flname;
    private String model_type = "";
    private String parent_id;
    private String root_id;
    private String thumbnail_path ;
    private int level = -1;
    private boolean isOpen = false ;
    private String report_type = Constants.REPORT_MINE ;

    public Boolean getFolder() {
        return share_showtype.endsWith("_folder") || model_type.endsWith("_folder") || template_showtype.endsWith("_folder");
    }

    public Boolean isEditAble(){
        return sign.equals("p");
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_clname() {
        return template_clname;
    }

    public void setTemplate_clname(String template_clname) {
        this.template_clname = template_clname;
    }

    public String getTemplate_flname() {
        return template_flname;
    }

    public void setTemplate_flname(String template_flname) {
        this.template_flname = template_flname;
    }

    public String getTemplate_showtype() {
        return template_showtype;
    }

    public void setTemplate_showtype(String template_showtype) {
        this.template_showtype = template_showtype;
    }

    public String getTemplate_parentid() {
        return template_parentid;
    }

    public void setTemplate_parentid(String template_parentid) {
        this.template_parentid = template_parentid;
    }

    public String getTemplate_rootid() {
        return template_rootid;
    }

    public void setTemplate_rootid(String template_rootid) {
        this.template_rootid = template_rootid;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    public String getShare_clname() {
        return share_clname;
    }

    public void setShare_clname(String share_clname) {
        this.share_clname = share_clname;
    }

    public String getShare_flname() {
        return share_flname;
    }

    public void setShare_flname(String share_flname) {
        this.share_flname = share_flname;
    }

    public String getShare_showtype() {
        return share_showtype;
    }

    public void setShare_showtype(String share_showtype) {
        this.share_showtype = share_showtype;
    }

    public String getShare_parentid() {
        return share_parentid;
    }

    public void setShare_parentid(String share_parentid) {
        this.share_parentid = share_parentid;
    }

    public String getShare_rootid() {
        return share_rootid;
    }

    public void setShare_rootid(String share_rootid) {
        this.share_rootid = share_rootid;
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

    public String getRes_level() {
        return res_level;
    }

    public void setRes_level(String res_level) {
        this.res_level = res_level;
    }

    public String getLevel_sort() {
        return level_sort;
    }

    public void setLevel_sort(String level_sort) {
        this.level_sort = level_sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCombination_id() {
        return combination_id;
    }

    public void setCombination_id(String combination_id) {
        this.combination_id = combination_id;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_clname() {
        return model_clname;
    }

    public void setModel_clname(String model_clname) {
        this.model_clname = model_clname;
    }

    public String getModel_flname() {
        return model_flname;
    }

    public void setModel_flname(String model_flname) {
        this.model_flname = model_flname;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getRoot_id() {
        return root_id;
    }

    public void setRoot_id(String root_id) {
        this.root_id = root_id;
    }

    public void setLevel(int level) {
        this.level = level ;
    }

    public int getLevel() {
        return level;
    }
}
