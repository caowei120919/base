package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.ReportFolderPresenter;
import com.datacvg.dimp.view.ReportFolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :管理画布文件夹详情
 */
public class ReportFolderActivity extends BaseActivity<ReportFolderView, ReportFolderPresenter>
        implements ReportFolderView, ReportListAdapter.OnReportListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_sort)
    ImageView imgRight ;
    @BindView(R.id.img_search)
    ImageView imgOther ;
    @BindView(R.id.recycler_reportOfFolder)
    RecyclerView recyclerReportOfFolder ;

    private ReportBean reportBean ;
    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private String folderType ;
    private ReportListAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_folder;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));

        reportBean = (ReportBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        folderType = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        if(reportBean == null || TextUtils.isEmpty(folderType)){
            finish();
            return;
        }
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_sort));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_search_of_report));
        switch (folderType){
            case Constants.REPORT_MINE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getModel_clname()
                        : reportBean.getModel_flname());
                break;

            case Constants.REPORT_SHARE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getShare_clname()
                        : reportBean.getShare_flname());
                break;

            case Constants.REPORT_TEMPLATE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getTemplate_clname()
                        : reportBean.getTemplate_flname());
                break;
        }

        adapter = new ReportListAdapter(mContext,folderType,reportBeans,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerReportOfFolder.setLayoutManager(linearLayoutManager);
        recyclerReportOfFolder.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getModel_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_SHARE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getShare_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getTemplate_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    @OnClick({R.id.img_sort,R.id.img_search,R.id.img_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();
                break;

            case R.id.img_search :
                PLog.e("跳转到搜索");
                break;

            case R.id.img_sort :
                PLog.e("排序");
                break;
        }
    }

    @Override
    public void getReportSuccess(ReportListBean data) {
        reportBeans.clear();
        reportBeans.addAll(data);
        adapter.notifyDataSetChanged();
    }

    /**
     * 报告被删除
     * @param reportBean
     */
    @Override
    public void onReportDelete(ReportBean reportBean) {

    }

    /**
     * 报告被添加到大屏
     * @param reportBean
     */
    @Override
    public void onReportAddToScreen(ReportBean reportBean) {

    }

    /**
     * 报告被下载
     * @param reportBean
     */
    @Override
    public void onReportDownload(ReportBean reportBean) {

    }
}
