package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.EmptyBoardView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-15
 * @Description :
 */
public class EmptyBoardPresenter extends BasePresenter<EmptyBoardView>{
    MobileApi api ;

    @Inject
    public EmptyBoardPresenter(MobileApi api) {
        this.api = api;
    }
}
