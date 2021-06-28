package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-04-20
 * @Description : 所有指标信息
 */


@Keep
public class AllIndexBean {

    private List<IndexDetailListBean> addAbleIndexInfo;

    public List<IndexDetailListBean> getAddAbleIndexInfo() {
        return addAbleIndexInfo;
    }

    public void setAddAbleIndexInfo(List<IndexDetailListBean> addAbleIndexInfo) {
        this.addAbleIndexInfo = addAbleIndexInfo;
    }
}
