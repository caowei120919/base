package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.MyIndexPresenter;
import com.datacvg.sempmobile.view.MyIndexView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-30
 * @Description : 我的指标
 */
public class MyIndexActivity extends BaseActivity<MyIndexView, MyIndexPresenter>
        implements MyIndexView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
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
        tvTitle.setText(resources.getString(R.string.digital_nervous));
    }

    @OnClick({R.id.img_left})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;
        }
    }
}
