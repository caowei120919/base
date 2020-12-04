package com.datacvg.dimp.greendao.bean;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description :  联系人实体对象
 */
@Keep
@Entity
public class ContactBean{
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

    /**
     * 是否为负责人选中(只适用于行动方案创建任务)
     */
    private boolean isHeadChecked = false ;

    public boolean isHeadChecked() {
        return isHeadChecked;
    }

    public void setHeadChecked(boolean headChecked) {
        isHeadChecked = headChecked;
    }

    public boolean isAssistantChecked() {
        return isAssistantChecked;
    }

    public void setAssistantChecked(boolean assistantChecked) {
        isAssistantChecked = assistantChecked;
    }

    /**
     * 是否为协助人选中(只适用于行动方案)
     */
    private boolean isAssistantChecked = false ;


    @Generated(hash = 943245095)
    public ContactBean(Long contact_id, String id, String department_id,
            String user_id, String name, boolean isHeadChecked,
            boolean isAssistantChecked) {
        this.contact_id = contact_id;
        this.id = id;
        this.department_id = department_id;
        this.user_id = user_id;
        this.name = name;
        this.isHeadChecked = isHeadChecked;
        this.isAssistantChecked = isAssistantChecked;
    }

    @Generated(hash = 1283900925)
    public ContactBean() {
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


    public boolean getIsHeadChecked() {
        return this.isHeadChecked;
    }

    public void setIsHeadChecked(boolean isHeadChecked) {
        this.isHeadChecked = isHeadChecked;
    }

    public boolean getIsAssistantChecked() {
        return this.isAssistantChecked;
    }

    public void setIsAssistantChecked(boolean isAssistantChecked) {
        this.isAssistantChecked = isAssistantChecked;
    }
}
