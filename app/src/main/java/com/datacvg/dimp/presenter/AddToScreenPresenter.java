package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.AddToScreenView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :
 */
public class AddToScreenPresenter extends BasePresenter<AddToScreenView>{
    MobileApi api ;

    @Inject
    public AddToScreenPresenter(MobileApi api) {
        this.api = api;
    }
}
