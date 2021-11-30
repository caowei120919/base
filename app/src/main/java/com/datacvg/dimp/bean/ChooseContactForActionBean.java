package com.datacvg.dimp.bean;

import androidx.annotation.Keep;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-29
 * @Description :
 */
@Keep
public class ChooseContactForActionBean implements Serializable {
    private String name ;
    /**
     * 层级
     */
    private Integer level = 0 ;
    private String resId ;
    private String parentId ;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    /**
     * 是否 为联系人
     */
    private Boolean isContact = false ;
    /**
     * 子数据
     */
    private List<ChooseContactForActionBean> childBeans = new ArrayList<>() ;

    public List<ChooseContactForActionBean> getChildBeans() {
        return childBeans;
    }

    public void setChildBeans(List<ChooseContactForActionBean> childBeans) {
        this.childBeans = childBeans;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getContact() {
        return isContact;
    }

    public void setContact(Boolean contact) {
        isContact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
