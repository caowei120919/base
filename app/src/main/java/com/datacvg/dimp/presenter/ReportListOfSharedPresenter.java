package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportListOfSharedView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfSharedPresenter extends BasePresenter<ReportListOfSharedView>{
    MobileApi api ;

    @Inject
    public ReportListOfSharedPresenter(MobileApi api) {
        this.api = api;
    }
}
