package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.ScreenSettingView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :
 */
public class ScreenSettingPresenter extends BasePresenter<ScreenSettingView>{
    MobileApi api ;

    @Inject
    public ScreenSettingPresenter(MobileApi api) {
        this.api = api;
    }
}
