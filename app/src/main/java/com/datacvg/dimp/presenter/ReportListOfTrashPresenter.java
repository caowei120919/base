package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportListOfTrashView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfTrashPresenter extends BasePresenter<ReportListOfTrashView>{
    MobileApi api ;

    @Inject
    public ReportListOfTrashPresenter(MobileApi api) {
        this.api = api;
    }
}
