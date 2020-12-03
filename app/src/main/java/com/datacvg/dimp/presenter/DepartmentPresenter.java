package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.DepartmentView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-02
 * @Description :
 */
public class DepartmentPresenter extends BasePresenter<DepartmentView>{
    MobileApi api ;

    @Inject
    public DepartmentPresenter(MobileApi api) {
        this.api = api;
    }
}
