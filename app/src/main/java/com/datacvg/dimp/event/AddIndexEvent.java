package com.datacvg.dimp.event;

import com.datacvg.dimp.bean.DimensionPositionBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-03
 * @Description :
 */
public class AddIndexEvent {
    private DimensionPositionBean dimensionPositionBean ;
    private String pageNo ;
    public AddIndexEvent(DimensionPositionBean dimensionPositionBean, String pageNo) {
        this.dimensionPositionBean = dimensionPositionBean ;
        this.pageNo = pageNo ;
    }

    public DimensionPositionBean getDimensionPositionBean() {
        return dimensionPositionBean;
    }

    public void setDimensionPositionBean(DimensionPositionBean dimensionPositionBean) {
        this.dimensionPositionBean = dimensionPositionBean;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
}
