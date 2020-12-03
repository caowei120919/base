package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ScreenBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface ScreenView extends MvpView {
    void getScreenSuccess(List<ScreenBean> resdata);
}
