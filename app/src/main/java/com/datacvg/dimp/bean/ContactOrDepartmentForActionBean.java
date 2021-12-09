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
    /**
     * 是否选中
     */
    private boolean isChecked = false ;
    /**
     * 是否可以选择
     */
    private boolean isUnableChoose = false ;

    public ContactOrDepartmentBean getContactOrDepartmentBean() {
        return contactOrDepartmentBean;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isUnableChoose() {
        return isUnableChoose;
    }

    public void setUnableChoose(boolean unableChoose) {
        isUnableChoose = unableChoose;
    }

    public void setContactOrDepartmentBean(ContactOrDepartmentBean contactOrDepartmentBean) {
        this.contactOrDepartmentBean = contactOrDepartmentBean;
    }

    public ContactOrDepartmentForActionBean(String id, String pId, int level,boolean isExpend, ContactOrDepartmentBean contactOrDepartmentBean) {
        super(id, pId,level,isExpend);
        this.contactOrDepartmentBean = contactOrDepartmentBean;
    }
}
