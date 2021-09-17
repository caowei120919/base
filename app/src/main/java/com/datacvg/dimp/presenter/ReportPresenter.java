package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ReportView;
import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class ReportPresenter extends BasePresenter<ReportView>{
    private MobileApi api ;

    @Inject
    public ReportPresenter(MobileApi api) {
        this.api = api ;
    }

}
