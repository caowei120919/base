package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.InComeDetailView;
import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-09
 * @Description :
 */
public class InComeDetailPresenter extends BasePresenter<InComeDetailView>{
    MobileApi api ;

    @Inject
    public InComeDetailPresenter(MobileApi api) {
        this.api = api;
    }
}
