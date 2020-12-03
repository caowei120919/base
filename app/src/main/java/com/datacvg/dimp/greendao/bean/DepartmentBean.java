package com.datacvg.dimp.greendao.bean;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description : 数据库部门实体
 */
@Keep
@Entity
public class DepartmentBean {
    /**
     * 存放数据库编号，唯一、自增长
     */
    @Id(autoincrement = true)
    private Long department_id ;

    /**
     * 设置唯一性，部门ID
     */
    @Index(unique = true)
    private String d_res_pkid;

    /**
     * 关联的部门ID
     */

    private String d_res_rootid ;

    private String res_level_sort ;

    private String d_res_id ;

    private String d_res_parentid ;

    private String d_res_flname ;

    private String d_res_clname ;

    private String res_level ;

    @Generated(hash = 1590762733)
    public DepartmentBean(Long department_id, String d_res_pkid,
                          String d_res_rootid, String res_level_sort, String d_res_id,
                          String d_res_parentid, String d_res_flname, String d_res_clname,
                          String res_level) {
        this.department_id = department_id;
        this.d_res_pkid = d_res_pkid;
        this.d_res_rootid = d_res_rootid;
        this.res_level_sort = res_level_sort;
        this.d_res_id = d_res_id;
        this.d_res_parentid = d_res_parentid;
        this.d_res_flname = d_res_flname;
        this.d_res_clname = d_res_clname;
        this.res_level = res_level;
    }

    @Generated(hash = 1119420877)
    public DepartmentBean() {
    }

    public Long getDepartment_id() {
        return this.department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }

    public String getD_res_pkid() {
        return this.d_res_pkid;
    }

    public void setD_res_pkid(String d_res_pkid) {
        this.d_res_pkid = d_res_pkid;
    }

    public String getD_res_rootid() {
        return this.d_res_rootid;
    }

    public void setD_res_rootid(String d_res_rootid) {
        this.d_res_rootid = d_res_rootid;
    }

    public String getRes_level_sort() {
        return this.res_level_sort;
    }

    public void setRes_level_sort(String res_level_sort) {
        this.res_level_sort = res_level_sort;
    }

    public String getD_res_id() {
        return this.d_res_id;
    }

    public void setD_res_id(String d_res_id) {
        this.d_res_id = d_res_id;
    }

    public String getD_res_parentid() {
        return this.d_res_parentid;
    }

    public void setD_res_parentid(String d_res_parentid) {
        this.d_res_parentid = d_res_parentid;
    }

    public String getD_res_flname() {
        return this.d_res_flname;
    }

    public void setD_res_flname(String d_res_flname) {
        this.d_res_flname = d_res_flname;
    }

    public String getD_res_clname() {
        return this.d_res_clname;
    }

    public void setD_res_clname(String d_res_clname) {
        this.d_res_clname = d_res_clname;
    }

    public String getRes_level() {
        return this.res_level;
    }

    public void setRes_level(String res_level) {
        this.res_level = res_level;
    }
}
