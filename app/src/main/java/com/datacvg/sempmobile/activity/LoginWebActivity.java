package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.LoginWebPresenter;
import com.datacvg.sempmobile.view.LoginWebView;

import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-18
 * @Description : 授权网页登录
 */
public class LoginWebActivity extends BaseActivity<LoginWebView, LoginWebPresenter>
        implements LoginWebView {
    private String uuid = "" ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_web;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        uuid = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        if(TextUtils.isEmpty(uuid)){
            finish();
            return;
        }
    }

    @OnClick({R.id.img_left,R.id.tv_title,R.id.btn_login,R.id.btn_cancel})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
            case R.id.tv_title :
            case R.id.btn_cancel :
                    finish();
                break;

            case R.id.btn_login :
                    getPresenter().loginWeb(uuid,Constants.token);
                break;
        }
    }

    /**
     * 扫码登录成功
     */
    @Override
    public void loginSuccess() {
        finish();
    }
}
