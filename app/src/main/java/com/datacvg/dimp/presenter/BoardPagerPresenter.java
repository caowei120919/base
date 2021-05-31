package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.BoardPagerView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class BoardPagerPresenter extends BasePresenter<BoardPagerView>{
    MobileApi api ;

    @Inject
    public BoardPagerPresenter(MobileApi api) {
        this.api = api;
    }
}
