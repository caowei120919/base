package com.datacvg.dimp.baseandroid.utils;

import android.app.Activity;
import android.content.Context;

import com.datacvg.dimp.baseandroid.manager.BaseAppManager;

/**
 * @author Tony
 * 在Activity上回调UI时，判断是否Activity被销毁
 * @version 1.0, 2017/12/18.
 * @since JDK1.7
 */
public abstract class UiRunnable implements Runnable {
    private final Context activity;

    public UiRunnable(Context activity) {
        this.activity = activity;
    }

    public void execute() {
        if (!(activity instanceof Activity)) {
            return;
        }
        if (!BaseAppManager.isActivityFinished((Activity) activity)) {
            run();
        }
    }
}
