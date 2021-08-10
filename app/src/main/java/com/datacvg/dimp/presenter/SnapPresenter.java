package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.SnapView;

import javax.inject.Inject;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-03
 * @Description :
 */
public class SnapPresenter extends BasePresenter<SnapView>{
    MobileApi api ;

    @Inject
    public SnapPresenter(MobileApi api) {
        this.api = api;
    }
}
