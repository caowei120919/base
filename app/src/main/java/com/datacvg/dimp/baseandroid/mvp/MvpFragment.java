package com.datacvg.dimp.baseandroid.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datacvg.dimp.baseandroid.utils.PLog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends RxFragment
        implements MvpDelegateCallback<V,P>, View.OnAttachStateChangeListener
        ,OnFragmentVisibilityChangedListener{
    private final String TAG = this.getClass().getSimpleName() ;
    protected FragmentMvpDelegate<V, P> mvpDelegate;
    /**
     * ParentActivity是否可见
     */
    private boolean mParentActivityVisible = false;
    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    private boolean mVisible = false;

    private MvpFragment mParentFragment;
    private OnFragmentVisibilityChangedListener mListener;

    public void setOnVisibilityChangedListener(OnFragmentVisibilityChangedListener listener) {
        mListener = listener;
    }

    @Inject
    protected P presenter;

    /**
     * Creates a new presenter instance,This method will be
     * called from {@link #onViewCreated(View, Bundle)}
     */
    @Override
    public abstract P createPresenter();

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

    /**
     * Get the mvp delegate. This is internally used for creating presenter, attaching
     * and detaching view from presenter.
     * @return {@link FragmentMvpDelegateImpl}
     */
    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this, this);
        }
        return mvpDelegate;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMvpDelegate().onAttach(activity);
        final Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof MvpFragment) {
            mParentFragment = ((MvpFragment) parentFragment);
            mParentFragment.setOnVisibilityChangedListener(this);
        }
        checkVisibility(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
        view.addOnAttachStateChangeListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getMvpDelegate().onHiddenChanged(hidden);
        // hidden 表示是否是隐藏的，后续 checkVisibility 里面的 mVisible 表示是否可见
        // 所以这两个应该是相反的
        checkVisibility(!hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getMvpDelegate().setUserVisibleHint(isVisibleToUser);
        checkVisibility(isVisibleToUser);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        getMvpDelegate().onViewAttachedToWindow(v);
        checkVisibility(true);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        getMvpDelegate().onViewDetachedFromWindow(v);
        v.removeOnAttachStateChangeListener(this);
        checkVisibility(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
        onActivityVisibilityChanged(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
        onActivityVisibilityChanged(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onDetach() {
        if (mParentFragment != null) {
            mParentFragment.setOnVisibilityChangedListener(null);
        }
        super.onDetach();
        getMvpDelegate().onDetach();
        checkVisibility(false);
        mParentFragment = null;
    }

    /**
     * ParentFragment可见性改变
     */
    @Override
    public void onFragmentVisibilityChanged(boolean visible) {
        checkVisibility(visible);
    }

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    public boolean isFragmentVisible() {
        PLog.e(TAG,"isFragmentVisible() == " + mVisible);
        return mVisible;
    }

    private void checkVisibility(boolean expected) {
        if (expected == mVisible){
            return;
        }
        final boolean parentVisible = mParentFragment == null ? mParentActivityVisible : mParentFragment.isFragmentVisible();
        final boolean superVisible = super.isVisible();
        final boolean hintVisible = getUserVisibleHint();
        final boolean visible = parentVisible && superVisible && hintVisible;
        PLog.e(TAG,String.format("==> checkVisibility = %s  ( parent = %s, super = %s, hint = %s )",
                visible, parentVisible, superVisible, hintVisible));
        if (visible != mVisible) {
            mVisible = visible;
            onVisibilityChanged(mVisible);
        }
    }

    /**
     * 可见性改变
     */
    protected void onVisibilityChanged(boolean visible) {
        PLog.e(TAG,"==> onFragmentVisibilityChanged = " + visible);
        if (mListener != null) {
            mListener.onFragmentVisibilityChanged(visible);
        }
    }

    /**
     * ParentActivity可见性改变
     */
    protected void onActivityVisibilityChanged(boolean visible) {
        mParentActivityVisible = visible;
        checkVisibility(visible);
    }
}
