package com.datacvg.dimp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.presenter.SplashPresenter;
import com.datacvg.dimp.view.SplashView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView{

    private final int DELAY_TIME = 1000 ;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
    /**
     * 判断当前activity是不是为当前应用启动的第一个activity
     * 如果不是，则重新启动应用
     */
        if(!mContext.isTaskRoot()){
            Intent intent = getIntent();
            if(intent != null){
                String action = intent.getAction();
                if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)){
                    finish();
                    return;
                }
            }
            DisplayUtils.fullScreen(mContext,true);
        }

        checkPermissions();
    }

    /**
     * 相关权限检查
     */
    private void checkPermissions() {
        new RxPermissions(mContext)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE)
                .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                        goToLogin();
                    }
                });
    }

    /**
     * 跳转
     */
    private void goToLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进入主程序页面
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        }, DELAY_TIME);
    }
}
