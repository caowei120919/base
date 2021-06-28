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
    private List<DimensionBean> indexPadDimension;
    private List<DimensionBean> indexPadOtherDimension;

    public List<DimensionBean> getSelectOtherDimension() {
        return indexPadOtherDimension;
    }

    public void setSelectOtherDimension(List<DimensionBean> selectOtherDimension) {
        this.indexPadOtherDimension = selectOtherDimension;
    }

    public List<DimensionBean> getSelectDimension() {
        return indexPadDimension;
    }

    public void setSelectDimension(List<DimensionBean> selectDimension) {
        this.indexPadDimension = selectDimension;
    }
}
