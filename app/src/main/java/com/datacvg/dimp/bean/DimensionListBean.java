package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description :
 */
@Keep
public class DimensionListBean {
    private List<DimensionBean> selectDimension;
    private List<DimensionBean> selectOtherDimension;

    public List<DimensionBean> getSelectOtherDimension() {
        return selectOtherDimension;
    }

    public void setSelectOtherDimension(List<DimensionBean> selectOtherDimension) {
        this.selectOtherDimension = selectOtherDimension;
    }

    public List<DimensionBean> getSelectDimension() {
        return selectDimension;
    }

    public void setSelectDimension(List<DimensionBean> selectDimension) {
        this.selectDimension = selectDimension;
    }
}
