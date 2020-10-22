package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.bean.ScreenDetailBean;
import com.datacvg.sempmobile.presenter.ScreenSettingPresenter;
import com.datacvg.sempmobile.view.ScreenSettingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description : 大屏报表图片设置
 * */
public class ScreenSettingActivity extends BaseActivity<ScreenSettingView, ScreenSettingPresenter>
        implements ScreenSettingView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_name)
    TextView tvName ;

    private ScreenDetailBean.ListBean bean ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen_setting;
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
        bean = (ScreenDetailBean.ListBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        tvTitle.setText(resources.getString(R.string.set));
        tvName.setText(bean.getImg_name());
    }

    @OnClick({R.id.img_left,R.id.btn_save})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.btn_save :

                break;
        }
    }
}
