package com.datacvg.sempmobile.baseandroid.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 所有activity工具类
 */
public abstract class MvpBaseActivity<V extends MvpView,P extends MvpPresenter<V>>
        extends RxAppCompatActivity implements MvpView, MvpDelegateCallback<V,P>{
    protected ActivityMvpDelegate mvpDelegate ;

    @Inject
    protected P presenter ;

    @Override
    @NonNull
    public abstract P createPresenter();

    @NonNull
    @Override
    public V getMvpView() {
        return (V)this;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    public ActivityMvpDelegate<V,P> getMvpDelegate() {
        if(this.mvpDelegate == null){
            this.mvpDelegate = new ActivityMvpDelegateImpl(this,this);
        }
        return mvpDelegate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvpDelegate().onContentChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMvpDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }
}