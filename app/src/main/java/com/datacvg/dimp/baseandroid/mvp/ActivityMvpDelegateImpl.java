package com.datacvg.dimp.baseandroid.mvp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description :
 */
public class ActivityMvpDelegateImpl<V extends MvpView,P extends MvpPresenter<V>>
        implements ActivityMvpDelegate {

    private MvpDelegateCallback<V, P> delegateCallback;
    protected Activity activity;

    /**
     * @param activity The Activity
     * @param delegateCallback The callback
     */
    public ActivityMvpDelegateImpl(@NonNull Activity activity, @NonNull MvpDelegateCallback<V, P> delegateCallback) {

        if (activity == null) {
            throw new NullPointerException("Activity is null!");
        }

        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        this.delegateCallback = delegateCallback;
        this.activity = activity;
    }

    /**
     * create a new presenter instance
     * @return The new created presenter instance
     */
    private P createPresenter() {
        P presenter = delegateCallback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from createPresenter() is null. Activity is " + activity);
        }

        return presenter;
    }

    private P getPresenter() {
        P presenter = delegateCallback.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        return presenter;
    }

    private V getMvpView() {
        V view = delegateCallback.getMvpView();
        if (view == null) {
            throw new NullPointerException("View returned from getMvpView() is null");
        }
        return view;
    }

    @Override
    public void onCreate(Bundle bundle) {
        P presenter = createPresenter();
        if (presenter == null) {
            throw new IllegalStateException("Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues");
        }

        delegateCallback.setPresenter(presenter);

        getPresenter().attachView(getMvpView());
    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onDestroy() {
        getPresenter().detachView();
    }

}
