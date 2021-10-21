package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.AddReportToScreenView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :
 */
public class AddReportToScreenPresenter extends BasePresenter<AddReportToScreenView>{
    MobileApi api ;

    @Inject
    public AddReportToScreenPresenter(MobileApi api) {
        this.api = api;
    }
}
