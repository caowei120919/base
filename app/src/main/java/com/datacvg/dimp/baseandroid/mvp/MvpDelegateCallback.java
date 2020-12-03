package com.datacvg.dimp.baseandroid.mvp;

import androidx.annotation.NonNull;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description :
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

    @NonNull
    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();
}
