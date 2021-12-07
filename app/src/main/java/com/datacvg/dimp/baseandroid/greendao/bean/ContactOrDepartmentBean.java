package com.datacvg.dimp.baseandroid.greendao.bean;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-06
 * @Description :
 */
@Keep
@Entity
public class ContactOrDepartmentBean {
    @Id(autoincrement = true)
    private Long id ;
    private String name;
    private String resId;
    private String parentId;
    private Boolean isExpend = false ;
    private Boolean isContact = false ;

    public Boolean getContact() {
        return isContact;
    }

    public void setContact(Boolean contact) {
        isContact = contact;
    }

    private int level = -1 ;

    @Generated(hash = 168670897)
    public ContactOrDepartmentBean(Long id, String name, String resId,
            String parentId, Boolean isExpend, Boolean isContact, int level) {
        this.id = id;
        this.name = name;
        this.resId = resId;
        this.parentId = parentId;
        this.isExpend = isExpend;
        this.isContact = isContact;
        this.level = level;
    }

    @Generated(hash = 1453604526)
    public ContactOrDepartmentBean() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getExpend() {
        return isExpend;
    }

    public void setExpend(Boolean expend) {
        isExpend = expend;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boolean getIsExpend() {
        return this.isExpend;
    }

    public void setIsExpend(Boolean isExpend) {
        this.isExpend = isExpend;
    }

    public Boolean getIsContact() {
        return this.isContact;
    }

    public void setIsContact(Boolean isContact) {
        this.isContact = isContact;
    }
}
