package com.datacvg.sempmobile.baseandroid.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * FileName: FragmentMvpDelegateImpl
 * Author: 曹伟
 * Date: 2019/9/16 18:57
 * Description:
 */

public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>> implements FragmentMvpDelegate<V, P> {

    private MvpDelegateCallback<V, P> delegateCallback;
    protected Fragment fragment;

    /**
     * @param fragment The Fragment
     * @param delegateCallback the DelegateCallback
     */
    public FragmentMvpDelegateImpl(@NonNull Fragment fragment, @NonNull MvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        if (fragment == null) {
            throw new NullPointerException("Fragment is null!");
        }

        this.fragment = fragment;
        this.delegateCallback = delegateCallback;
    }

    /**
     * calls {@link MvpDelegateCallback#createPresenter()}
     * to create a new presenter instance
     * @return The new created presenter instance
     */
    private P createPresenter() {
        P presenter = delegateCallback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from createPresenter() is null. Activity is " + getActivity());
        }

        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        P presenter = createPresenter();
        if (presenter == null) {
            throw new IllegalStateException("Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues");
        }

        delegateCallback.setPresenter(presenter);
        getPresenter().attachView(getMvpView());
    }

    @NonNull
    private Activity getActivity() {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity returned by Fragment.getActivity() is null. Fragment is " + fragment);
        }

        return activity;
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
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Activity activity) {

    }

    @Override
    public void onCreate(Bundle saved) {

    }

    @Override
    public void onStart() {

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
    public void onDestroyView() {
        getPresenter().detachView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }
}
