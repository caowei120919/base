package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-15
 * @Description :
 */
@Keep
public class AddDepartmentToContactEvent {
    private List<String> selectDepartmentIds = new ArrayList<>() ;
    public AddDepartmentToContactEvent(List<String> selectDepartmentIds) {
        this.selectDepartmentIds = selectDepartmentIds ;
    }

    public List<String> getSelectDepartmentIds() {
        return selectDepartmentIds;
    }

    public void setSelectDepartmentIds(List<String> selectDepartmentIds) {
        this.selectDepartmentIds = selectDepartmentIds;
    }
}
