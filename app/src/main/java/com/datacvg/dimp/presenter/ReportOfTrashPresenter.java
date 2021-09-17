package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportOfTrashView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public class ReportOfTrashPresenter extends BasePresenter<ReportOfTrashView>{
    MobileApi api ;

    @Inject
    public ReportOfTrashPresenter(MobileApi api) {
        this.api = api;
    }
}
