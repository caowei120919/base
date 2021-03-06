package com.datacvg.dimp.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.datacvg.dimp.BuildConfig;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.StringUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.dimp.bean.UserLoginBean;
import com.datacvg.dimp.presenter.LoginPresenter;
import com.datacvg.dimp.view.LoginView;
import com.squareup.haha.perflib.Main;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-24
 * @Description : 登录页面
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    private String companyCode = "" ;
    private String userName = "" ;
    private String password = "" ;

    @BindView(R.id.ed_code)
    EditText edCode ;

    @BindView(R.id.ed_userName)
    EditText edUserName ;

    @BindView(R.id.ed_pwd)
    EditText edPwd ;

    @BindView(R.id.cb_rememberUser)
    CheckBox cbRememberUser ;

    @BindView(R.id.cb_userVPN)
    CheckBox cbUserVPN ;

    @BindView(R.id.btn_login)
    Button btnLogin ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 企业码输入监听
     */
    @OnTextChanged(value = R.id.ed_code,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onCodeTextChange(Editable editable){
        companyCode = editable.toString().trim();
    }

    /**
     * 用户名输入监听
     */
    @OnTextChanged(value = R.id.ed_userName,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onUserNameTextChange(Editable editable){
        userName = editable.toString().trim();
    }

    /**
     * 密码输入监听
     */
    @OnTextChanged(value = R.id.ed_pwd,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onPwdTextChange(Editable editable){
        password = editable.toString().trim();
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
        cbRememberUser.setChecked(PreferencesHelper
                .get(Constants.USER_CHECK_REMEMBER,false));
        companyCode = PreferencesHelper.get(Constants.USER_COMPANY_CODE,BuildConfig.DEBUG ? "datacvg" : "");
        userName = PreferencesHelper.get(Constants.USER_ID,BuildConfig.DEBUG ? "windy" : "");
        password = PreferencesHelper.get(Constants.USER_PWD,BuildConfig.DEBUG ? "111111" : "");

        if(!StringUtils.isEmpty(companyCode)){
            edCode.setText(companyCode);
        }
        if(!StringUtils.isEmpty(userName)){
            edUserName.setText(userName);
        }
        if(!StringUtils.isEmpty(password)){
            edPwd.setText(password);
        }
    }

    @OnClick({R.id.btn_login,R.id.tv_settingVpn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login :
                if(!AndroidUtils.isNetworkAvailable()){
                    ToastUtils.showLongToast(R.string.please_check_the_network);
                    return;
                }
                if (TextUtils.isEmpty(companyCode)){
                    ToastUtils.showLongToast(resources.getString(R.string.please_enter_merchant));
                    return;
                }
                if(TextUtils.isEmpty(userName)){
                    ToastUtils.showLongToast(mContext.getResources()
                            .getString(R.string.please_enter_the_user_name));
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    ToastUtils.showLongToast(mContext.getResources()
                            .getString(R.string.please_enter_your_password));
                    return;
                }
                if(!cbUserVPN.isChecked()){
                    getPresenter().checkUrlOrVersion(companyCode,userName,password);
                }
                break;

            case R.id.tv_settingVpn :
                    startActivity(new Intent(mContext,SettingVpnActivity.class));
                break;
        }
    }

    /**
     * 登录成功回调
     * @param baseBean
     */
    @Override
    public void loginSuccess(BaseBean<UserLoginBean> baseBean) {
        Constants.saveUser(baseBean.getResdata()
                ,cbRememberUser.isChecked(),password,companyCode);
        mContext.startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
