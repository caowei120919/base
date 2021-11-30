package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ChooseContactFromActionView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-25
 * @Description :
 */
public class ChooseContactFromActionPresenter extends BasePresenter<ChooseContactFromActionView>{
    MobileApi api ;

    @Inject
    public ChooseContactFromActionPresenter(MobileApi api) {
        this.api = api;
    }
}
