package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.DimensionPositionListBean;
import com.datacvg.dimp.bean.OtherDimensionBean;
import com.datacvg.dimp.bean.PageItemListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface DigitalView extends MvpView {
    /**
     * 获取数字神经多页数据成功
     * @param pageItemBeans
     */
    void getDigitalPageSuccess(PageItemListBean pageItemBeans);
}
