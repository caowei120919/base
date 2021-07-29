package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.event.BudgetItemEvent;
import com.datacvg.dimp.event.SelectPageEvent;
import com.datacvg.dimp.presenter.BudgetPresenter;
import com.datacvg.dimp.view.BudgetView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description : 指标绩效
 */
public class BudgetFragment extends BaseFragment<BudgetView, BudgetPresenter> implements BudgetView {
    @BindView(R.id.tv_pageTime)
    TextView tvPageTime ;
    @BindView(R.id.tv_timeType)
    TextView tvTimeType ;
    @BindView(R.id.tv_orgName)
    TextView tvOrgName ;
    @BindView(R.id.tv_proLine)
    TextView tvProLine ;
    @BindView(R.id.tv_proName)
    TextView tvProName ;
    @BindView(R.id.tv_areaLine)
    TextView tvAreaLine ;
    @BindView(R.id.tv_areaName)
    TextView tvAreaName ;

    private PageItemBean pageItemBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        pageItemBean = (PageItemBean) getArguments().getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
        setTimeValue();
        setDimension();
    }

    @Override
    protected void setupData() {


    }

    private void setTimeValue() {
        tvTimeType.setVisibility(View.VISIBLE);
        switch (pageItemBean.getTime_type()){
            case "month" :
                if(TextUtils.isEmpty(pageItemBean.getTimeVal())){
                    tvPageTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH,""));
                }else{
                    StringBuilder month = new StringBuilder(pageItemBean.getTimeVal());
                    month.insert(4,"/");
                    tvPageTime.setText(month);
                }
                tvTimeType.setText(resources.getString(R.string.month));
                break;
            case "year" :
                if(TextUtils.isEmpty(pageItemBean.getTimeVal())){
                    tvPageTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_YEAR,""));
                }else{
                    tvPageTime.setText(pageItemBean.getTimeVal());
                }
                tvTimeType.setText(resources.getString(R.string.year));
                break;
            default:
                if(TextUtils.isEmpty(pageItemBean.getTimeVal())){
                    tvPageTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_DAY,""));
                }else{
                    StringBuilder day = new StringBuilder(pageItemBean.getTimeVal());
                    day.insert(6,"/").insert(4,"/");
                    tvPageTime.setText(day);
                }
                tvTimeType.setText(resources.getString(R.string.day));
                break;
        }
    }

    private void setDimension() {
        if(!TextUtils.isEmpty(pageItemBean.getmOrgName())){
            tvOrgName.setVisibility(View.VISIBLE);
            tvOrgName.setText(pageItemBean.getmOrgName());
        }
        if(!TextUtils.isEmpty(pageItemBean.getmFuName())){
            tvProLine.setVisibility(View.VISIBLE);
            tvProName.setVisibility(View.VISIBLE);
            tvProName.setText(pageItemBean.getmFuName());
        }
        if(!TextUtils.isEmpty(pageItemBean.getMpName())){
            tvAreaName.setVisibility(View.VISIBLE);
            tvAreaLine.setVisibility(View.VISIBLE);
            tvAreaName.setText(pageItemBean.getMpName());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectPageEvent event){
        pageItemBean = event.getPageItemBean() ;
        setTimeValue();
        setDimension();
    }

    public static BudgetFragment newInstance(PageItemBean pageItemBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,pageItemBean);
        BudgetFragment fragment = new BudgetFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
