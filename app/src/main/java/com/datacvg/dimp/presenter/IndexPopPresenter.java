package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.IndexPopView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class IndexPopPresenter extends BasePresenter<IndexPopView>{
    MobileApi api ;

    @Inject
    public IndexPopPresenter(MobileApi api) {
        this.api = api;
    }
}
