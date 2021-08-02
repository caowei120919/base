package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportListView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ReportListPresenter extends BasePresenter<ReportListView> {
    MobileApi api ;

    @Inject
    public ReportListPresenter(MobileApi api) {
        this.api = api;
    }
}
