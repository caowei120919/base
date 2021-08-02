package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ActionListView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ActionListPresenter extends BasePresenter<ActionListView>{
    MobileApi api ;

    @Inject
    public ActionListPresenter(MobileApi api) {
        this.api = api;
    }
}
