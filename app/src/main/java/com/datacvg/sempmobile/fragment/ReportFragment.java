package com.datacvg.sempmobile.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.adapter.ReportAdapter;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.LanguageUtils;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.bean.ReportBean;
import com.datacvg.sempmobile.bean.ReportListBean;
import com.datacvg.sempmobile.presenter.ReportPresenter;
import com.datacvg.sempmobile.view.ReportView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 管理画布
 */
public class ReportFragment extends BaseFragment<ReportView, ReportPresenter> implements ReportView
        , ReportAdapter.OnReportClickListener {

    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_reportOfMine)
    TextView tvReportOfMine ;
    @BindView(R.id.tv_reportToShare)
    TextView tvReportToShare ;
    @BindView(R.id.recycler_report)
    RecyclerView recyclerReport ;
    @BindView(R.id.rel_folder)
    RelativeLayout relFolder ;
    @BindView(R.id.tv_folderName)
    TextView tvFolderName ;

    private String reportDisplayModel = Constants.REPORT_GRID;
    private String reportType = Constants.REPORT_MINE ;
    private ReportAdapter adapter ;
    private int parentLevel = -1 ;
    private String parentId =  "" ;
    /**
     * 总的数据，做存储使用
     */
    private List<ReportBean> reportBeans = new ArrayList<>();

    /**
     *
     */
    private List<ReportBean> reportDisPlayBeans = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager ;
    private GridLayoutManager gridLayoutManager ;
    private ReportItemDecoration reportItemDecoration ;

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

        linearLayoutManager = new LinearLayoutManager(mContext);
        gridLayoutManager = new GridLayoutManager(mContext,2);
        reportItemDecoration = new ReportItemDecoration();

        adapter = new ReportAdapter(mContext,reportDisPlayBeans,reportType,this);
        recyclerReport.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerReport.addItemDecoration(reportItemDecoration);
        recyclerReport.setAdapter(adapter);
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

    @OnClick({R.id.img_left,R.id.tv_reportOfMine,R.id.tv_reportToShare,R.id.rel_folder})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    reportDisPlayBeans.clear();
                    if(reportDisplayModel.equals(Constants.REPORT_GRID)){
                        imgLeft.setImageBitmap(BitmapFactory
                                .decodeResource(resources,R.mipmap.report_menu_list));
                        reportDisplayModel = Constants.REPORT_LIST ;
                        adapter.setDisplayType(reportDisplayModel);
                        recyclerReport.setLayoutManager(linearLayoutManager);
                    }else{
                        imgLeft.setImageBitmap(BitmapFactory
                                .decodeResource(resources,R.mipmap.report_menu_grid));
                        reportDisplayModel = Constants.REPORT_GRID ;
                        adapter.setDisplayType(reportDisplayModel);
                        recyclerReport.setLayoutManager(gridLayoutManager);
                    }
                    displayReport();
                break;

            case R.id.tv_reportOfMine :
                tvReportOfMine.setSelected(true);
                tvReportToShare.setSelected(false);
                reportType = Constants.REPORT_MINE ;
                if (adapter != null){
                    adapter.setReportType(reportType);
                }
                getPresenter().getReport(reportType,String.valueOf(System.currentTimeMillis()));
                break;

            case R.id.tv_reportToShare :
                tvReportOfMine.setSelected(false);
                tvReportToShare.setSelected(true);
                reportType = Constants.REPORT_SHARE ;
                if (adapter != null){
                    adapter.setReportType(reportType);
                }
                getPresenter().getReport(reportType,String.valueOf(System.currentTimeMillis()));
                break;

            case R.id.rel_folder :
                reportDisPlayBeans.clear();
                if(parentLevel == 1){
                    relFolder.setVisibility(View.GONE);
                 }else{
                    relFolder.setVisibility(View.VISIBLE);
                }
                switch (reportType){
                    case Constants.REPORT_MINE :
                        for (ReportBean bean : reportBeans){
                            if(bean.getParent_id().equals(parentId)){
                                reportDisPlayBeans.add(bean);
                            }
                        }
                    break;

                    case Constants.REPORT_SHARE :
                        for (ReportBean bean : reportBeans){
                            if(bean.getShare_parentid().equals(parentId)){
                                reportDisPlayBeans.add(bean);
                            }
                        }
                        break;
                }
                break;
        }
    }

    /**
     * 获取报告列表成功
     * @param data
     */
    @Override
    public void getReportSuccess(ReportListBean data) {
        reportBeans.clear();
        reportBeans.addAll(data);
        reportDisPlayBeans.clear();
        displayReport();
    }

    private void displayReport() {
        String rootId = "" ;
        switch (reportType){
            case Constants.REPORT_MINE :
                for (ReportBean bean : reportBeans){
                    if(bean.getParent_id().equals("0")){
                        rootId = bean.getModel_id() ;
                    }
                }
                for (ReportBean bean : reportBeans){
                    if(bean.getParent_id().equals(rootId)
                            && !bean.getParent_id().equals("0")){
                        bean.setLevel(1);
                        reportDisPlayBeans.add(bean);
                    }
                }
                break;

            case Constants.REPORT_SHARE :
                for (ReportBean bean : reportBeans){
                    if(bean.getShare_parentid().equals("0")){
                        rootId = bean.getShare_id() ;
                    }
                }
                for (ReportBean bean : reportBeans){
                    if(bean.getShare_parentid().equals(rootId)
                            && !bean.getShare_parentid().equals("0")){
                        bean.setLevel(1);
                        reportDisPlayBeans.add(bean);
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 报告被点击
     * @param reportBean
     */
    @Override
    public void onReportClick(ReportBean reportBean) {

    }

    /**
     * 文件夹被点击
     * @param reportBean
     */
    @Override
    public void onGridFolderClick(ReportBean reportBean) {
        String rootId = "" ;
        parentLevel = reportBean.getLevel() ;
        reportDisPlayBeans.clear();
        switch (reportType){
            case Constants.REPORT_MINE :
                rootId = reportBean.getModel_id();
                parentId = reportBean.getParent_id() ;
                tvFolderName.setText(LanguageUtils.isZh(mContext)
                        ? reportBean.getModel_clname() : reportBean.getModel_flname());
                for (ReportBean bean : reportBeans){
                    if(bean.getParent_id().equals(rootId)){
                        bean.setLevel(parentLevel + 1);
                        reportDisPlayBeans.add(bean);
                    }
                }
                break;

            case Constants.REPORT_SHARE :
                rootId = reportBean.getShare_id();
                parentId = reportBean.getShare_parentid() ;
                tvFolderName.setText(LanguageUtils.isZh(mContext)
                        ? reportBean.getShare_clname() : reportBean.getShare_flname());
                for (ReportBean bean : reportBeans){
                    if(bean.getShare_parentid().equals(rootId)){
                        bean.setLevel(parentLevel + 1);
                        reportDisPlayBeans.add(bean);
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
        relFolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListReportClick(int position,ReportBean reportBean) {
        String rootId = "" ;
        parentLevel = reportBean.getLevel() ;
        List<ReportBean> childReportBeans = new ArrayList<>();
        if (reportBean.isOpen()){
            childReportBeans.addAll(reportDisPlayBeans) ;
        }
        switch (reportType){
            case Constants.REPORT_MINE :
                rootId = reportBean.getModel_id();
                parentId = reportBean.getParent_id() ;
                tvFolderName.setText(LanguageUtils.isZh(mContext)
                        ? reportBean.getModel_clname() : reportBean.getModel_flname());
                for (ReportBean bean : reportBeans){
                    if(bean.getParent_id().equals(rootId)){
                        bean.setLevel(parentLevel + 1);
                        if(reportBean.isOpen()){
                            childReportBeans.remove(bean);
                        }else{
                            childReportBeans.add(bean);
                        }
                    }
                }
                break;

            case Constants.REPORT_SHARE :
                rootId = reportBean.getShare_id();
                parentId = reportBean.getShare_parentid() ;
                tvFolderName.setText(LanguageUtils.isZh(mContext)
                        ? reportBean.getShare_clname() : reportBean.getShare_flname());
                for (ReportBean bean : reportBeans){
                    if(bean.getShare_parentid().equals(rootId)){
                        bean.setLevel(parentLevel + 1);
                        if(reportBean.isOpen()){
                            childReportBeans.remove(bean);
                        }else{
                            childReportBeans.add(bean);
                        }
                    }
                }
                break;
        }
        if(reportBean.isOpen()){
            reportDisPlayBeans.clear();
            reportDisPlayBeans.addAll(childReportBeans);
        }else{
            reportDisPlayBeans.addAll(position + 1,childReportBeans);
        }
        reportBean.setOpen(!reportBean.isOpen());
        adapter.notifyDataSetChanged();
    }

    /**
     * 区分展示形式而设置间距
     */
    private class ReportItemDecoration extends RecyclerView.ItemDecoration{
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                switch (reportDisplayModel){
                    case Constants.REPORT_GRID :
                        outRect.top = (int) resources.getDimension(R.dimen.W20);
                        return;

                    default:
                        outRect.top = (int) resources.getDimension(R.dimen.W2);
                        return;
                }
            }
    }
}
