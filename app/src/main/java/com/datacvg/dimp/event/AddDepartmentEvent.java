package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.greendao.bean.DepartmentBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-11
 * @Description :
 */
@Keep
public class AddDepartmentEvent {
    private List<String> selectDepartmentIds = new ArrayList<>() ;

    public AddDepartmentEvent(List<String> selectDepartmentIds) {
        this.selectDepartmentIds = selectDepartmentIds;
    }

    public List<String> getSelectDepartmentIds() {
        return selectDepartmentIds;
    }

    public void setSelectDepartmentIds(List<String> selectDepartmentIds) {
        this.selectDepartmentIds = selectDepartmentIds;
    }
}
