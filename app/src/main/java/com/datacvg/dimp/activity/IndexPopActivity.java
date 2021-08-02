package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.fragment.ActionListFragment;
import com.datacvg.dimp.fragment.BoardFragment;
import com.datacvg.dimp.fragment.BudgetFragment;
import com.datacvg.dimp.fragment.RemarkFragment;
import com.datacvg.dimp.fragment.ReportListFragment;
import com.datacvg.dimp.presenter.IndexPopPresenter;
import com.datacvg.dimp.view.IndexPopView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class IndexPopActivity extends BaseActivity<IndexPopView, IndexPopPresenter>
        implements IndexPopView {
    @BindView(R.id.tv_reportList)
    TextView tvReportList ;
    @BindView(R.id.tv_action)
    TextView tvAction ;
    @BindView(R.id.tv_remark)
    TextView tvRemark ;

    private FragmentTransaction fragmentTransaction;

    private ReportListFragment reportListFragment ;
    private ActionListFragment actionListFragment ;
    private RemarkFragment remarkFragment ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_pop;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        showFragment(R.id.tv_reportList);
        tvReportList.setSelected(true);
        tvAction.setSelected(false);
        tvRemark.setSelected(false);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.rel_detail,R.id.tv_reportList,R.id.tv_action,R.id.tv_remark})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rel_detail :
                finish();
                break;

            case R.id.tv_reportList :
                    showFragment(R.id.tv_reportList);
                    tvReportList.setSelected(true);
                    tvAction.setSelected(false);
                    tvRemark.setSelected(false);
                break;

            case R.id.tv_action :
                    showFragment(R.id.tv_action);
                    tvReportList.setSelected(false);
                    tvAction.setSelected(true);
                    tvRemark.setSelected(false);
                break;

            case R.id.tv_remark :
                    showFragment(R.id.tv_remark);
                    tvReportList.setSelected(false);
                    tvAction.setSelected(false);
                    tvRemark.setSelected(true);
                break;
        }
    }

    private void showFragment(int viewId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (viewId){
            case R.id.tv_reportList :
                if(null != reportListFragment){
                    fragmentTransaction.show(reportListFragment);
                }else{
                    reportListFragment = new ReportListFragment();
                    fragmentTransaction.add(R.id.content,reportListFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case R.id.tv_action :
                if(null != actionListFragment){
                    fragmentTransaction.show(actionListFragment);
                }else{
                    actionListFragment = new ActionListFragment();
                    fragmentTransaction.add(R.id.content,actionListFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case R.id.tv_remark :
                if(null != remarkFragment){
                    fragmentTransaction.show(remarkFragment);
                }else{
                    remarkFragment = new RemarkFragment();
                    fragmentTransaction.add(R.id.content,remarkFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(null != reportListFragment){
            fragmentTransaction.hide(reportListFragment);
        }
        if(null != actionListFragment){
            fragmentTransaction.hide(actionListFragment);
        }
        if(null != remarkFragment){
            fragmentTransaction.hide(remarkFragment);
        }
    }
}
