package com.datacvg.dimp.baseandroid.mvp;

import androidx.annotation.UiThread;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 基类P层
 */
public interface MvpPresenter<V extends MvpView> {

    @UiThread
    void attachView(V view);

    @UiThread
    void detachView();
}
