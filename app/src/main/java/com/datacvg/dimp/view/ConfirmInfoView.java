package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-18
 * @Description :
 */
public interface ConfirmInfoView extends MvpView {
    /**
     * 投屏成功
     */
    void forScreenSuccess();

    /**
     * 投屏失败
     */
    void forScreenFailed();
}
