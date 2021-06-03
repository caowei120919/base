package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ChartDetailView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class ChartDetailPresenter extends BasePresenter<ChartDetailView>{
    MobileApi api ;

    @Inject
    public ChartDetailPresenter(MobileApi api) {
        this.api = api;
    }
}
