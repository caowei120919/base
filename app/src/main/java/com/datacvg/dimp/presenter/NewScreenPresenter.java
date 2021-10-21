package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.NewScreenView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :
 */
public class NewScreenPresenter extends BasePresenter<NewScreenView>{
    MobileApi api ;

    @Inject
    public NewScreenPresenter(MobileApi api) {
        this.api = api;
    }
}
