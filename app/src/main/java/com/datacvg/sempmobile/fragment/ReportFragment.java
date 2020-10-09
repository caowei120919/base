package com.datacvg.sempmobile.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.ReportPresenter;
import com.datacvg.sempmobile.view.ReportView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 管理画布
 */
public class ReportFragment extends BaseFragment<ReportView, ReportPresenter> implements ReportView {

    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_reportOfMine)
    TextView tvReportOfMine ;
    @BindView(R.id.tv_reportToShare)
    TextView tvReportToShare ;

    private String reportDisplayModel = Constants.REPORT_GRID;
    private String reportType = Constants.REPORT_MINE ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        imgLeft.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.report_menu_grid));
        tvReportOfMine.setSelected(true);
        tvReportToShare.setSelected(false);
        getReport();
    }

    /**
     * 获取报告
     */
    private void getReport() {
        getPresenter().getReport(reportType,String.valueOf(System.currentTimeMillis()));
    }

    @OnClick({R.id.img_left,R.id.tv_reportOfMine,R.id.tv_reportToShare})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    if(reportDisplayModel.equals(Constants.REPORT_GRID)){
                        imgLeft.setImageBitmap(BitmapFactory
                                .decodeResource(resources,R.mipmap.report_menu_list));
                        reportDisplayModel = Constants.REPORT_LIST ;
                    }else{
                        imgLeft.setImageBitmap(BitmapFactory
                                .decodeResource(resources,R.mipmap.report_menu_grid));
                        reportDisplayModel = Constants.REPORT_GRID ;
                    }
                break;

            case R.id.tv_reportOfMine :
                tvReportOfMine.setSelected(true);
                tvReportToShare.setSelected(false);
                reportType = Constants.REPORT_MINE ;
                getPresenter().getReport(reportType,String.valueOf(System.currentTimeMillis()));
                break;

            case R.id.tv_reportToShare :
                tvReportOfMine.setSelected(false);
                tvReportToShare.setSelected(true);
                reportType = Constants.REPORT_SHARE ;
                getPresenter().getReport(reportType,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }
}
