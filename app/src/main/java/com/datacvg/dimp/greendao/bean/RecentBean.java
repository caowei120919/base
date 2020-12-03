package com.datacvg.dimp.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description :最近联系人
 * */
@Keep
@Entity
public class RecentBean {
    /**
     * 存放数据库编号，唯一、自增长
     */
    @Id(autoincrement = true)
    private Long contact_id ;

    /**
     * 设置唯一性，用户ID
     */
    @Index(unique = true)
    private String id;

    /**
     * 关联的部门ID
     */
    private String department_id ;

    private String user_id;

    private String name;

    private String department_name ;

    @Generated(hash = 1259266746)
    public RecentBean(Long contact_id, String id, String department_id,
                      String user_id, String name, String department_name) {
        this.contact_id = contact_id;
        this.id = id;
        this.department_id = department_id;
        this.user_id = user_id;
        this.name = name;
        this.department_name = department_name;
    }

    @Generated(hash = 1697461393)
    public RecentBean() {
    }

    public Long getContact_id() {
        return this.contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment_id() {
        return this.department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment_name() {
        return this.department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}
