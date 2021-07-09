package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.greendao.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-09
 * @Description :
 */
@Keep
public class SelectChooseContactEvent {
    private List<ContactBean> contactBeans = new ArrayList<>() ;

    public SelectChooseContactEvent(List<ContactBean> selectContactBeans) {
        this.contactBeans = selectContactBeans ;
    }

    public List<ContactBean> getContactBeans() {
        return contactBeans;
    }

    public void setContactBeans(List<ContactBean> contactBeans) {
        this.contactBeans = contactBeans;
    }
}
