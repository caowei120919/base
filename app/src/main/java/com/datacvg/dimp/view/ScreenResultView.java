package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-19
 * @Description :
 */
public interface ScreenResultView extends MvpView {
    /**
     * 投放失败
     */
    void forScreenFailed();

    /**
     * 投放成功
     */
    void forScreenSuccess();
}
