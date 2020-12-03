package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.greendao.bean.DepartmentBean;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-11
 * @Description :
 */
@Keep
public class DeleteDepartmentEvent {
    private DepartmentBean bean ;
    public DeleteDepartmentEvent(DepartmentBean bean) {
        this.bean = bean ;
    }

    public DepartmentBean getBean() {
        return bean;
    }

    public void setBean(DepartmentBean bean) {
        this.bean = bean;
    }
}
