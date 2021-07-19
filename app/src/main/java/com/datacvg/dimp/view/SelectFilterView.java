package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.DimensionBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-19
 * @Description :
 */
public interface SelectFilterView extends MvpView {
    void getDimensionSuccess(List<DimensionBean> selectDimension);

    void getOtherDimensionSuccess(List<DimensionBean> selectOtherDimension, String tag);
}
