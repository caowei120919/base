package com.datacvg.sempmobile.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.VPNConfigBean;
import com.datacvg.sempmobile.presenter.SettingVpnPresenter;
import com.datacvg.sempmobile.view.SettingVpnView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.tbruyelle.rxpermissions2.RxPermissions;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-21
 * @Description :
 */
public class SettingVpnActivity extends BaseActivity<SettingVpnView, SettingVpnPresenter>
        implements SettingVpnView {

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.img_other)
    ImageView imgOther ;
    @BindView(R.id.rg_vpnModel)
    RadioGroup rgVPNModel ;

    private String mSemfAddress ;
    private String mLicenseCode ;
    private String mVPNAddress ;
    private String mVPNAccount ;
    private String mVPNPassword ;
    private String mVpnModel = Constants.VPN_MODEL_EASY ;
    private IntentIntegrator mIntentIntegrator ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_vpn;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.configuration));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.qr_code));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.img_left,R.id.img_right,R.id.img_other,R.id.radio_easy,R.id.radio_L3})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            /**
             * 扫描二维码
             */
            case R.id.img_right :
                mIntentIntegrator = new IntentIntegrator(mContext);
                mIntentIntegrator.setCaptureActivity(SettingVpnActivity.class);
                mIntentIntegrator.initiateScan();
                break;

            /**
             * 二维码展示
             */
            case R.id.img_other :
                new RxPermissions(mContext)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                        .subscribe(new RxObserver<Boolean>(){
                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                VPNConfigBean vpnConfigBean = new VPNConfigBean() ;
                                vpnConfigBean.setLicenseurl(mLicenseCode);
                                vpnConfigBean.setSemfurl(mSemfAddress);
                                vpnConfigBean.setVpnmodel(mVpnModel);
                                vpnConfigBean.setVpnpassword(mVPNPassword);
                                vpnConfigBean.setVpnurl(mVPNAddress);
                                vpnConfigBean.setVpnuser(mVPNAccount);

                                Intent intent = new Intent(mContext,QRCodeActivity.class);
                                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,vpnConfigBean);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtils.showShortToast(resources.getString(R.string.confirm));
                            }
                        });
                break;

            case R.id.radio_easy :
                ((RadioButton)view).setChecked(true);
                mVpnModel = Constants.VPN_MODEL_EASY ;
                break;

            case R.id.radio_L3 :
                ((RadioButton)view).setChecked(true);
                mVpnModel = Constants.VPN_MODEL_L3 ;
                break;
        }
    }

    /**
     * SEMF服务地址
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_address,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onSemfAddressTextChange(Editable editable){
        mSemfAddress = editable.toString().trim();
    }

    /**
     * license地址
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_license,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onLicenseTextChange(Editable editable){
        mLicenseCode = editable.toString().trim();
    }

    /**
     * vpn地址
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_vpn,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onVPNTextChange(Editable editable){
        mVPNAddress = editable.toString().trim();
    }

    /**
     * vpn账号输入
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_account,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAccountTextChange(Editable editable){
        mVPNAccount = editable.toString().trim();
    }

    @OnTextChanged(value = R.id.ed_pwd,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onPasswordTextChange(Editable editable){
        mVPNPassword = editable.toString().trim();
    }
}
