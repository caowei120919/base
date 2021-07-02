package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.BudgetView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public class BudgetPresenter extends BasePresenter<BudgetView>{
    MobileApi api ;

    @Inject
    public BudgetPresenter(MobileApi api) {
        this.api = api;
    }
}
