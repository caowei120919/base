package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.TestRealView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-28
 * @Description :
 */
public class TestRealPresenter extends BasePresenter<TestRealView>{

    MobileApi api ;

    @Inject
    public TestRealPresenter(MobileApi api) {
        this.api = api ;
    }
}
