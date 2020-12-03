package com.datacvg.dimp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import com.datacvg.dimp.MyApplication;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.BaseApplication;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.dragger.component.ActivityComponent;
import com.datacvg.dimp.baseandroid.dragger.component.DaggerActivityComponent;
import com.datacvg.dimp.baseandroid.dragger.component.MyAppComponent;
import com.datacvg.dimp.baseandroid.dragger.module.ActivityModule;
import com.datacvg.dimp.baseandroid.mvp.BaseDoubleClickExitHelper;
import com.datacvg.dimp.baseandroid.mvp.MvpBaseActivity;
import com.datacvg.dimp.baseandroid.mvp.MvpBasePresenter;
import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.lang.ref.WeakReference;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public abstract class BaseActivity<V extends MvpView,P extends MvpBasePresenter<V>>
        extends MvpBaseActivity<V,P> implements MvpView{
    protected final String TAG = this.getClass().getSimpleName() ;
    private Unbinder mUnbinder;
    public static BaseDoubleClickExitHelper mDoubleClickExitHelper;
    private ActivityComponent mActivityComponent;
    private WeakReference<Activity> softActivity ;
    protected BaseActivity mContext ;
    protected Resources resources ;

    /**
     * Instantiate a presenter instance
     * @return The {@link com.datacvg.dimp.baseandroid.mvp.MvpPresenter} for this view
     */
    @Override
    @NonNull
    public P createPresenter() {
        setupComponent(MyApplication.getMyAppComponent());
        initInject();
        return presenter;
    }

    protected void setupComponent(MyAppComponent applicationComponent) {
        mActivityComponent = DaggerActivityComponent.builder()
                .myAppComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String language = PreferencesHelper.get(Constants.APP_LANGUAGE,Constants.LANGUAGE_CHINESE);
        if(language.equals(Constants.LANGUAGE_AUTO)){
            super.attachBaseContext(newBase);
        }else{
            super.attachBaseContext(LanguageUtils.attachBaseContext(newBase,language));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        resources = mContext.getResources() ;
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setup(savedInstanceState);
        softActivity = new WeakReference<Activity>(this);
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

    protected abstract void initInject();

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
        EventBus.getDefault().unregister(this);
        BaseApplication.getAppManager().removeActivity(softActivity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode,event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str){

    }
}
