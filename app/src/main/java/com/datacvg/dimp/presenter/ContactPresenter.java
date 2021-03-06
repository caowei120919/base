package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ContactView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-02
 * @Description :
 */
public class ContactPresenter extends BasePresenter<ContactView>{
    MobileApi api ;

    @Inject
    public ContactPresenter(MobileApi api) {
        this.api = api;
    }
}
