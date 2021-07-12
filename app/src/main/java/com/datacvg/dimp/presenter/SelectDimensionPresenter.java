package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.SelectDimensionView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-12
 * @Description :
 */
public class SelectDimensionPresenter extends BasePresenter<SelectDimensionView>{
    MobileApi api ;

    @Inject
    public SelectDimensionPresenter(MobileApi api) {
        this.api = api;
    }
}
