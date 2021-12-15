package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.greendao.bean.ContactBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-08
 * @Description :
 */
@Keep
public class ContactEvent {
    private List<String> selectIds ;

    public ContactEvent(List<String> selectIds) {
        this.selectIds = selectIds;
    }

    public List<String> getSelectIds() {
        return selectIds;
    }

    public void setSelectIds(List<String> selectIds) {
        this.selectIds = selectIds;
    }
}
