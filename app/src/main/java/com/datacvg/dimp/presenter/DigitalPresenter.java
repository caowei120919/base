package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.view.DigitalView;
import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class DigitalPresenter extends BasePresenter<DigitalView>{
    MobileApi api ;

    @Inject
    public DigitalPresenter(MobileApi api) {
        this.api = api ;
    }
}
