package com.datacvg.sempmobile.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.datacvg.sempmobile.BuildConfig;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.greendao.bean.VpnInfo;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.baseandroid.utils.ShareUtils;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.StringUtils;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.VPNConfigBean;
import com.datacvg.sempmobile.presenter.SettingVpnPresenter;
import com.datacvg.sempmobile.view.SettingVpnView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
    @BindView(R.id.ed_pwd)
    EditText edPwd ;
    @BindView(R.id.ed_account)
    EditText edAccount ;
    @BindView(R.id.ed_vpn)
    EditText edVpn ;
    @BindView(R.id.ed_license)
    EditText edLicense ;
    @BindView(R.id.ed_address)
    EditText edAddress ;
    @BindView(R.id.tv_hint_license)
    TextView tvHintLicense ;

    private String mSemfAddress ;
    private String mLicenseCode ;
    private String mVPNAddress ;
    private String mVPNAccount ;
    private String mVPNPassword ;
    private String mVpnModel = Constants.VPN_MODEL_EASY ;
    private IntentIntegrator mIntentIntegrator ;

    private VPNConfigBean vpnConfigBean ;

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
        tvHintLicense.setVisibility(BuildConfig.APP_STAND ? View.VISIBLE : View.GONE);
        edLicense.setVisibility(BuildConfig.APP_STAND ? View.VISIBLE : View.GONE);
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.qr_code));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        if(BuildConfig.APP_STAND){
            edLicense.setText(PreferencesHelper.get(Constants.VPN_LICENSE_URL,""));
        }
        edAccount.setText(PreferencesHelper.get(Constants.VPN_USER,""));
        edAddress.setText(PreferencesHelper.get(Constants.VPN_SEMF_URL,""));
        edVpn.setText(PreferencesHelper.get(Constants.VPN_URL,""));
        edPwd.setText(PreferencesHelper.get(Constants.VPN_PASSWORD,""));
        if(PreferencesHelper.get(Constants.VPN_MODEL,"").equals(Constants.VPN_MODEL_L3)){
            rgVPNModel.check(R.id.radio_L3);
            mVpnModel = Constants.VPN_MODEL_L3 ;
        }
    }

    @OnClick({R.id.img_left,R.id.img_right,R.id.img_other,R.id.radio_easy,R.id.radio_L3
            ,R.id.btn_save})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            /**
             * 扫描二维码
             */
            case R.id.img_right :
                new RxPermissions(mContext)
                        .request(Manifest.permission.CAMERA
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                        .subscribe(new RxObserver<Boolean>(){
                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {      //授权通过拍摄照片
                                    mIntentIntegrator = new IntentIntegrator(mContext);
                                    mIntentIntegrator.setCaptureActivity(ScanActivity.class);
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_SCAN
                                            ,Constants.SCAN_FOR_VPN);
                                    mIntentIntegrator.initiateScan();
                                }
                            }
                        });
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

            case R.id.btn_save :
                    saveVpnConfig();
                break;
        }
    }

    /**
     * 保存配置的vpn信息
     */
    private void saveVpnConfig() {
        PreferencesHelper.put(Constants.VPN_LICENSE_URL,mLicenseCode);
        PreferencesHelper.put(Constants.VPN_SEMF_URL,mSemfAddress);
        PreferencesHelper.put(Constants.VPN_USER,mVPNAccount);
        PreferencesHelper.put(Constants.VPN_URL,mVPNAddress);
        PreferencesHelper.put(Constants.VPN_MODEL,mVpnModel);
        PreferencesHelper.put(Constants.VPN_PASSWORD,mVPNPassword);
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

    /**
     * 密码输入监听
     * @param editable
     */
    @OnTextChanged(value = R.id.ed_pwd,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onPasswordTextChange(Editable editable){
        mVPNPassword = editable.toString().trim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Constants.RESULT_SCAN_RESULT :
                vpnConfigBean
                        = (VPNConfigBean) data.getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
                PLog.e(new Gson().toJson(data.getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN)));
                resetView(vpnConfigBean);
                break;

            case RESULT_CANCELED :

                break;

            case RESULT_OK :
                    if(requestCode == IntentIntegrator.REQUEST_CODE){
                        IntentResult result
                                = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                        vpnConfigBean = new Gson()
                                .fromJson(result.getContents(),VPNConfigBean.class);
                        PLog.e(result.getContents());
                        resetView(vpnConfigBean);
                    }else {
                        ToastUtils.showShortToast(resources
                                .getString(R.string.this_qr_code_is_not_supported));
                    }
                break;

            default:
                ToastUtils.showShortToast(resources
                        .getString(R.string.this_qr_code_is_not_supported));
                break;
        }
    }

    /**
     * 根据二维码结果填充数据
     * @param vpnConfigBean
     */
    private void resetView(VPNConfigBean vpnConfigBean) {
        edPwd.setText(vpnConfigBean.getVpnpassword() != null ? vpnConfigBean.getVpnpassword() : "");
        edAccount.setText(vpnConfigBean.getVpnuser() != null ? vpnConfigBean.getVpnuser() : "");
        edVpn.setText(vpnConfigBean.getVpnurl() != null ? vpnConfigBean.getVpnurl() : "");
        if(BuildConfig.APP_STAND){
            edLicense.setText(vpnConfigBean.getLicenseurl() != null ? vpnConfigBean.getLicenseurl() : "");
        }
        edAddress.setText(vpnConfigBean.getSemfurl() != null ? vpnConfigBean.getSemfurl() : "");
        if(vpnConfigBean.getVpnmodel() != null
                && vpnConfigBean.getVpnmodel().equals(Constants.VPN_MODEL_L3)){
            rgVPNModel.check(R.id.radio_L3);
            mVpnModel = Constants.VPN_MODEL_L3 ;
        }else{
            rgVPNModel.check(R.id.radio_easy) ;
            mVpnModel = Constants.VPN_MODEL_EASY ;
        }
    }
}
