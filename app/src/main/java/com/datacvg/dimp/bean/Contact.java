package com.datacvg.dimp.bean;


import com.datacvg.dimp.greendao.bean.ContactBean;

import org.greenrobot.greendao.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-11
 * @Description :
 */
@Keep
public class Contact {
    private ContactBean bean ;
    private String name ;

    public Contact() {
    }

    public Contact(String name, int mType) {
        this.name = name;
        this.mType = mType;
    }

    public Contact(ContactBean bean, int type) {
        this.bean = bean ;
        mType = type;
    }

    public Contact(ContactBean bean, String name) {
        this.bean = bean;
        this.name = name;
    }

    public ContactBean getBean() {
        return bean;
    }

    public void setBean(ContactBean bean) {
        this.bean = bean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    private int mType;

    public int getmType() {
        return mType;
    }
}
