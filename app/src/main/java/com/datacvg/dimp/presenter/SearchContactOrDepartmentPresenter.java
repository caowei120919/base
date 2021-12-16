package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.SearchContactOrDepartmentView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-15
 * @Description :
 */
public class SearchContactOrDepartmentPresenter extends BasePresenter<SearchContactOrDepartmentView>{
    MobileApi api ;

    @Inject
    public SearchContactOrDepartmentPresenter(MobileApi api) {
        this.api = api;
    }
}
