package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.RemarkView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class RemarkPresenter extends BasePresenter<RemarkView>{
    MobileApi api ;

    @Inject
    public RemarkPresenter(MobileApi api) {
        this.api = api;
    }
}
