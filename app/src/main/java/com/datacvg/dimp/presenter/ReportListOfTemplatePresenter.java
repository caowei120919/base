package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportListOfTemplateView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfTemplatePresenter extends BasePresenter<ReportListOfTemplateView>{
    MobileApi api ;

    @Inject
    public ReportListOfTemplatePresenter(MobileApi api) {
        this.api = api;
    }
}
