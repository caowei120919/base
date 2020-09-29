package com.datacvg.sempmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datacvg.sempmobile.BuildConfig;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.CacheUtils;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.SettingPresenter;
import com.datacvg.sempmobile.view.SettingView;

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
    }

    @OnClick({R.id.img_left,R.id.rel_clearCache,R.id.switch_login,R.id.rel_language
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
             * 开启关闭指纹登录
             */
            case R.id.switch_login :

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
}
