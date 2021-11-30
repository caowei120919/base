package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-30
 * @Description :
 */
@Keep
public class ContactOrDepartmentBean implements Serializable {
    private String name ;
    private Boolean isContact = false ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getContact() {
        return isContact;
    }

    public void setContact(Boolean contact) {
        isContact = contact;
    }
}
