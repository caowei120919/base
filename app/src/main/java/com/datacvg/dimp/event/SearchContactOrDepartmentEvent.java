package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import java.util.ArrayList;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-15
 * @Description :
 */
@Keep
public class SearchContactOrDepartmentEvent {
    private ArrayList<String> selectId = new ArrayList<>() ;
    private boolean isContact = true ;
    public SearchContactOrDepartmentEvent(ArrayList<String> selectIds) {
        this.selectId = selectIds ;
    }

    public boolean isContact() {
        return isContact;
    }

    public void setContact(boolean contact) {
        isContact = contact;
    }

    public ArrayList<String> getSelectId() {
        return selectId;
    }

    public void setSelectId(ArrayList<String> selectId) {
        this.selectId = selectId;
    }
}
