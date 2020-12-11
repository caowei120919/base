package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.IndexTreeView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
public class IndexTreePresenter extends BasePresenter<IndexTreeView>{
    MobileApi api ;

    @Inject
    public IndexTreePresenter(MobileApi api) {
        this.api = api;
    }
}
