package com.datacvg.dimp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.datacvg.dimp.MyApplication;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.LoginActivity;
import com.datacvg.dimp.baseandroid.BaseApplication;
import com.datacvg.dimp.baseandroid.dragger.component.DaggerFragmentComponent;
import com.datacvg.dimp.baseandroid.dragger.component.FragmentComponent;
import com.datacvg.dimp.baseandroid.dragger.component.MyAppComponent;
import com.datacvg.dimp.baseandroid.dragger.module.FragmentModule;
import com.datacvg.dimp.baseandroid.mvp.MvpFragment;
import com.datacvg.dimp.baseandroid.mvp.MvpPresenter;
import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.baseandroid.mvp.OnFragmentVisibilityChangedListener;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.event.RefreshTokenEvent;
import com.squareup.leakcanary.RefWatcher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public abstract class BaseFragment<V extends MvpView,P extends MvpPresenter<V>>
        extends MvpFragment<V,P> implements MvpView, OnFragmentVisibilityChangedListener {

    protected Unbinder mUnbinder;
    protected Context mContext ;
    protected Resources resources ;
    private FragmentComponent mFragmentComponent;
    protected Boolean hasLoaded = false ;


    /**
     * Creates a new presenter instance,This method will be
     * called from {@link #onViewCreated(View, Bundle)}
     */
    @Override
    public P createPresenter() {
        setupComponent(MyApplication.getMyAppComponent());
        initInject();
        return presenter;
    }

    protected void setupComponent(MyAppComponent applicationComponent) {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .myAppComponent(applicationComponent)
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container,false);
        mContext = getActivity() ;
        resources = mContext.getResources() ;
        mUnbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        setOnVisibilityChangedListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(savedInstanceState, view);
    }

    private void setup(Bundle savedInstanceState, View rootView) {
        StatusBarUtil.setRootViewFitsSystemWindows(getActivity(),false);
        StatusBarUtil.setTranslucentStatus(getActivity());
        if (!StatusBarUtil.setStatusBarDarkTheme(getActivity(), true)) {
            StatusBarUtil.setStatusBarColor(getActivity(),mContext.getResources()
                    .getColor(R.color.c_FFFFFF));
        }
        setupView(rootView);
    }

    protected abstract int getLayoutId();

    protected abstract void initInject();

    protected abstract void setupView(View rootView);

    protected abstract void setupData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        PLog.e("onDestroy");
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        if (BaseApplication.DEBUGMODE) {
            RefWatcher refWatcher = BaseApplication.getRefWatcher();
            refWatcher.watch(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshTokenEvent event){
        mContext.startActivity(new Intent(mContext,LoginActivity.class));
    }

    @Override
    public void onFragmentVisibilityChanged(boolean visible) {
        super.onFragmentVisibilityChanged(visible);
        if(visible && !hasLoaded){
            hasLoaded = true ;
            setupData();
        }
    }
}
