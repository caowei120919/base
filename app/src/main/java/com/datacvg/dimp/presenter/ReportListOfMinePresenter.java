package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportListOfMineView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfMinePresenter extends BasePresenter<ReportListOfMineView>{
    MobileApi api ;

    @Inject
    public ReportListOfMinePresenter(MobileApi api) {
        this.api = api;
    }
}
