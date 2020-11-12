package com.datacvg.sempmobile.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.BaseApplication;
import com.datacvg.sempmobile.baseandroid.mvp.BaseDoubleClickExitHelper;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-08
 * @Description :
 */
public abstract class SimpleBaseActivity extends RxAppCompatActivity {

    private final String TAG = this.getClass().getSimpleName() ;
    private WeakReference<Activity> softActivity ;
    private static BaseDoubleClickExitHelper mDoubleClickExitHelper;
    protected SimpleBaseActivity mContext ;
    protected Resources resources ;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        softActivity = new WeakReference<>(this);
        mContext = this ;
        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        resources = mContext.getResources();
        mUnbinder = ButterKnife.bind(this);
        setup(savedInstanceState);
        BaseApplication.getAppManager().addActivity(softActivity);
        mDoubleClickExitHelper = new BaseDoubleClickExitHelper(this);
        PLog.e(TAG);
    }

    private void setup(Bundle savedInstanceState) {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this,mContext.getResources()
                    .getColor(R.color.c_FFFFFF));
        }
        setupView();
        setupData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void setupView();

    protected abstract void setupData(Bundle savedInstanceState);

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        BaseApplication.getAppManager().removeActivity(softActivity);
    }
}
