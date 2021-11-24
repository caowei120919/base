package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SearchReportAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.SearchReportPresenter;
import com.datacvg.dimp.view.SearchReportView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-20
 * @Description :报告搜索
 */
public class SearchReportActivity extends BaseActivity<SearchReportView, SearchReportPresenter>
        implements SearchReportView, SearchReportAdapter.OnSearchReportClick {
    @BindView(R.id.edit_searchText)
    EditText editSearchText ;
    @BindView(R.id.recycler_searchReport)
    RecyclerView recyclerSearchReport ;

    private String searchText ;
    private String reportType = Constants.REPORT_MINE;
    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private List<ReportBean> reportSearchBeans = new ArrayList<>() ;
    private SearchReportAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        adapter = new SearchReportAdapter(mContext,this,reportSearchBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerSearchReport.setLayoutManager(manager);
        recyclerSearchReport.setAdapter(adapter);
    }

    /**
     * 根据关键字搜索报告信息
     */
    private void getReportForSearchText() {
        reportSearchBeans.clear();
        for (ReportBean bean : reportBeans){
            if(bean.getModel_clname().contains(searchText)
                    || bean.getShare_clname().contains(searchText)
                    || bean.getTemplate_clname().contains(searchText)){
                reportSearchBeans.add(bean);
            }
        }
        if (TextUtils.isEmpty(searchText)){
            reportSearchBeans.clear();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        reportType = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_BEAN);
        switch (reportType){
            case Constants.REPORT_MINE :
                getPresenter().getReport(reportType
                        ,Constants.REPORT_MINE_PARENT_ID
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_SHARE :
                getPresenter().getReport(reportType
                        ,Constants.REPORT_SHARE_PARENT_ID
                        ,String.valueOf(System.currentTimeMillis()));
                break;

            case Constants.REPORT_TEMPLATE :
                getPresenter().getReport(reportType
                        ,Constants.REPORT_TEMPLATE_PARENT_ID
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    @OnTextChanged(value = R.id.edit_searchText,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onSearchTextChange(Editable editable){
        searchText = editable.toString().trim();
        getReportForSearchText();
    }

    @OnClick({R.id.tv_cancel,R.id.rel_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel :
                    finish();
                break;

            case R.id.rel_clear :
                searchText = "" ;
                editSearchText.setText("");
                break;
        }
    }

    @Override
    public void getReportSuccess(ReportListBean data) {
        reportBeans.clear();
        reportBeans.addAll(data);
    }

    /**
     * 报告点击时间
     * @param reportBean
     */
    @Override
    public void onReportClick(ReportBean reportBean) {
        if(reportBean.getFolder()){
            Intent intent = new Intent(mContext,ReportFolderActivity.class);
            intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,reportType);
            intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
            mContext.startActivity(intent);
        }else{
            Intent intent = new Intent(mContext, ReportDetailActivity.class) ;
            intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean) ;
            mContext.startActivity(intent);
        }
    }
}
