package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.SearchIndexView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-13
 * @Description :
 */
public class SearchIndexPresenter extends BasePresenter<SearchIndexView>{
    MobileApi api ;

    @Inject
    public SearchIndexPresenter(MobileApi api) {
        this.api = api;
    }
}
