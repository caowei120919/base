package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.bean.tree.Node;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-07
 * @Description :
 */
@Keep
public class ContactOrDepartmentForActionBean extends Node<ContactOrDepartmentForActionBean> {
    private ContactOrDepartmentBean contactOrDepartmentBean ;

    public ContactOrDepartmentBean getContactOrDepartmentBean() {
        return contactOrDepartmentBean;
    }

    public void setContactOrDepartmentBean(ContactOrDepartmentBean contactOrDepartmentBean) {
        this.contactOrDepartmentBean = contactOrDepartmentBean;
    }

    public ContactOrDepartmentForActionBean(String id, String pId, int level,boolean isExpend, ContactOrDepartmentBean contactOrDepartmentBean) {
        super(id, pId,level,isExpend);
        this.contactOrDepartmentBean = contactOrDepartmentBean;
    }
}
