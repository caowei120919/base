package com.datacvg.dimp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.presenter.ChartDetailPresenter;
import com.datacvg.dimp.view.ChartDetailView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class ChartDetailActivity extends BaseActivity<ChartDetailView, ChartDetailPresenter>
        implements ChartDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;

    private DimensionPositionBean.IndexPositionBean bean ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chart_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        Drawable drawable = resources.getDrawable(R.mipmap.icon_drop);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null,null,drawable,null);
        tvTitle.setCompoundDrawablePadding(40);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        bean = (DimensionPositionBean.IndexPositionBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        switch (bean.getTime_type()){
            case "month" :
                tvTitle.setText(TimeUtils.getNewStrDateForStr(PreferencesHelper
                        .get(Constants.USER_DEFAULT_MONTH,""),TimeUtils.FORMAT_YM_EN));
                break;
            case "year" :
                tvTitle.setText(TimeUtils.getNewStrDateForStr(PreferencesHelper
                        .get(Constants.USER_DEFAULT_YEAR,""),TimeUtils.FORMAT_Y));
                break;
            default:
                tvTitle.setText(TimeUtils.getNewStrDateForStr(PreferencesHelper
                        .get(Constants.USER_DEFAULT_DAY,""),TimeUtils.FORMAT_YMD));
                break;
        }
    }

    @OnClick({R.id.img_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;
        }
    }
}
