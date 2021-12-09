package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-08
 * @Description :
 */
@Keep
public class ChooseUserForActionEvent {

    private ContactOrDepartmentForActionBean contactOrDepartmentForActionBean ;
    private boolean isHeadChoose = false ;

    public boolean isHeadChoose() {
        return isHeadChoose;
    }

    public void setHeadChoose(boolean headChoose) {
        isHeadChoose = headChoose;
    }

    public ChooseUserForActionEvent(ContactOrDepartmentForActionBean contactOrDepartmentForActionBean, boolean isHeadChoose) {
        this.contactOrDepartmentForActionBean = contactOrDepartmentForActionBean ;
        this.isHeadChoose = isHeadChoose ;
    }

    public ContactOrDepartmentForActionBean getContactOrDepartmentForActionBean() {
        return contactOrDepartmentForActionBean;
    }

    public void setContactOrDepartmentForActionBean(ContactOrDepartmentForActionBean contactOrDepartmentForActionBean) {
        this.contactOrDepartmentForActionBean = contactOrDepartmentForActionBean;
    }
}
