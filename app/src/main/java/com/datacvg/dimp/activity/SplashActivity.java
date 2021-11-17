package com.datacvg.dimp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.datacvg.dimp.BuildConfig;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.UserLoginBean;
import com.datacvg.dimp.presenter.SplashPresenter;
import com.datacvg.dimp.view.SplashView;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView{

    private final int DELAY_TIME = 1000 ;
    private boolean REMEMBER_USER = false ;
    private String companyCode = "" ;
    private String userName = "" ;
    private String password = "" ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        REMEMBER_USER = PreferencesHelper
                .get(Constants.USER_CHECK_REMEMBER,false) ;
        companyCode = PreferencesHelper.get(Constants.USER_COMPANY_CODE, BuildConfig.DEBUG ? "datacvg" : "");
        userName = PreferencesHelper.get(Constants.USER_LOGIN_NAME,BuildConfig.DEBUG ? "windy" : "");
        password = PreferencesHelper.get(Constants.USER_LOGIN_PASSWORD,BuildConfig.DEBUG ? "111111" : "");
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
                        if(REMEMBER_USER){
                            String url = "";
                            switch (companyCode){
                                case Constants.DEV_CODE :
                                    url = Constants.BASE_DEV_MERCHANT;
                                    break;

                                case Constants.TEST_CODE :
                                    url = Constants.BASE_TEST_MERCHANT;
                                    break;

                                default:
                                    url = String.format(Constants.BASE_MERCHANT,companyCode);
                                    break;
                            }
                            Constants.BASE_MOBILE_URL = url ;
                            RetrofitUrlManager.getInstance().setRun(true);
                            RetrofitUrlManager.getInstance()
                                    .setGlobalDomain(url);
                            getPresenter().login(userName,password);
                        }else{
                            goToLogin();
                        }
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


    @Override
    public void loginSuccess(BaseBean baseBean) {
        UserLoginBean userLoginBean = new Gson().fromJson(new Gson().toJson(baseBean.getResdata()),UserLoginBean.class);
        Constants.saveUser(userLoginBean
                ,REMEMBER_USER,password,companyCode);
        mContext.startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
