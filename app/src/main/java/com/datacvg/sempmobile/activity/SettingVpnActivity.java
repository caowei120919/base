package com.datacvg.sempmobile.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.SettingVpnPresenter;
import com.datacvg.sempmobile.view.SettingVpnView;

import butterknife.BindView;
import butterknife.OnClick;

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
        tvTitle.setText(resources.getString(R.string.confirm));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.qr_code));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.img_left,R.id.img_right,R.id.img_other})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            /**
             * 扫描二维码
             */
            case R.id.img_right :

                break;

            /**
             * 二维码展示
             */
            case R.id.img_other :

                break;
        }
    }
}
