package com.datacvg.sempmobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.datacvg.sempmobile.baseandroid.BaseApplication;
import com.datacvg.sempmobile.baseandroid.mvp.BaseDoubleClickExitHelper;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.lang.ref.WeakReference;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-08
 * @Description :
 */
public abstract class SimpleBaseActivity extends RxAppCompatActivity {

    private final String TAG = this.getClass().getSimpleName() ;
    private WeakReference<Activity> softActivity ;
    private static BaseDoubleClickExitHelper mDoubleClickExitHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        softActivity = new WeakReference<>(this);
        setContentView(getLayoutId());
        setup(savedInstanceState);
        BaseApplication.getAppManager().addActivity(softActivity);
        mDoubleClickExitHelper = new BaseDoubleClickExitHelper(this);
        PLog.e(TAG);
    }

    private void setup(Bundle savedInstanceState) {
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
        BaseApplication.getAppManager().removeActivity(softActivity);
    }
}
