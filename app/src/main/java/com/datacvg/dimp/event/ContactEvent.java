package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.greendao.bean.ContactBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-08
 * @Description :
 */
@Keep
public class ContactEvent {
    private ContactBean contactBean ;

    public ContactEvent(ContactBean contactBean) {
        this.contactBean = contactBean ;
    }

    public ContactBean getContactBean() {
        return contactBean;
    }

    public void setContactBean(ContactBean contactBean) {
        this.contactBean = contactBean;
    }
}
