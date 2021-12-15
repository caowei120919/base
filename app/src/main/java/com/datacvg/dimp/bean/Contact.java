package com.datacvg.dimp.bean;


import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.greendao.bean.ContactBean;

import org.greenrobot.greendao.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-11
 * @Description :
 */
@Keep
public class Contact {
    private ContactOrDepartmentBean bean ;
    private String name ;

    public Contact(ContactOrDepartmentBean contactBean, String name, boolean checked) {
        this.bean = contactBean;
        this.name = name;
        this.isChecked = checked ;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    /**
     * 是否被选中,目标为人员
     */
    private boolean isChecked = false ;

    public Contact() {
    }

    public Contact(String name, int mType) {
        this.name = name;
        this.mType = mType;
    }

    public Contact(ContactOrDepartmentBean bean, int type) {
        this.bean = bean ;
        mType = type;
    }

    public Contact(ContactOrDepartmentBean bean, String name) {
        this.bean = bean;
        this.name = name;
    }

    public ContactOrDepartmentBean getBean() {
        return bean;
    }

    public void setBean(ContactOrDepartmentBean bean) {
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

    private String departmentId ;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
