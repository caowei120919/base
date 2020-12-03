package com.datacvg.dimp.baseandroid.mvp;

import android.os.Bundle;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description :
 */
public interface ActivityMvpDelegate<V extends MvpView,P extends MvpPresenter<V>> {
    /**
     * This method must be called from {#onCreate(Bundle)}.
     * This method internally creates the presenter and attaches the view to it.
     */
    void onCreate(Bundle bundle);

    /**
     * This method must be called from { #onContentChanged()}
     */
    void onContentChanged();

    /**
     * This method must be called from { #onStart()}
     */
    void onStart();

    /**
     * This method must be called from { #onPostCreate(Bundle)}
     */
    void onPostCreate(Bundle savedInstanceState);

    /**
     * This method must be called from { #onResume()}
     */
    void onResume();

    /**
     * This method must be called from { #onPause()}
     */
    void onPause();

    /**
     * This method must be called from { #onSaveInstanceState(Bundle)}
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * This method must be called from { #onStop()}
     */
    void onStop();

    /**
     * This method must be called from { #onRestart()}
     */
    void onRestart();

    /**
     * This method must be called from { #onDestroy()}}.
     * This method internally detaches the view from presenter
     */
    void onDestroy();
}
