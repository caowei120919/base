package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.datacvg.dimp.BuildConfig;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.CacheUtils;
import com.datacvg.dimp.baseandroid.utils.FingerPrintUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.event.RebuildTableEvent;
import com.datacvg.dimp.presenter.SettingPresenter;
import com.datacvg.dimp.view.SettingView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-28
 * @Description :设置
 */
public class SettingActivity extends BaseActivity<SettingView, SettingPresenter>
        implements SettingView{
    @BindView(R.id.tv_version)
    TextView tvVersion ;

    @BindView(R.id.tv_title)
    TextView tvTitle ;

    @BindView(R.id.tv_cache)
    TextView tvCache ;

    @BindView(R.id.switch_fingerprint)
    CheckBox switchFingerprint ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.set));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvVersion.setText(BuildConfig.VERSION_NAME);
        tvCache.setText(CacheUtils.getTotalCacheSize(mContext));

        switchFingerprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (FingerPrintUtils.supportFingerprint(mContext)){

                }
            }
        });
    }

    @OnClick({R.id.img_left,R.id.rel_clearCache,R.id.rel_language
            ,R.id.rel_moduleSetting})
    public void OnCLick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();
                break;

            /**
             * 清空缓存
             */
            case R.id.rel_clearCache :
                    CacheUtils.clearAllCache(mContext);
                break;

            /**
             * 语言设置
             */
            case R.id.rel_language :
                startActivity(new Intent(mContext,LanguageSettingActivity.class));
                break;

            /**
             * 模块设置
             */
            case R.id.rel_moduleSetting :
                startActivity(new Intent(mContext,ModuleSettingActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RebuildTableEvent event){
        PLog.e("caowei");
    }
}
