package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.ScreenBean;
import com.datacvg.sempmobile.bean.ScreenListBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface ScreenView extends MvpView {
    void getScreenSuccess(List<ScreenBean> resdata);
}
