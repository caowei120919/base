package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.DimensionListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-28
 * @Description :
 */
public interface TestRealView extends MvpView {
    void getDimensionSuccess(DimensionListBean data);
}
