package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.IndexTreeActivity;
import com.datacvg.dimp.adapter.BudgetIndexAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.ChatTypeRequestBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.presenter.BudgetPresenter;
import com.datacvg.dimp.view.BudgetView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description : 指标绩效
 */
public class BudgetFragment extends BaseFragment<BudgetView, BudgetPresenter> implements BudgetView, BudgetIndexAdapter.IndexClickListener {
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
    @BindView(R.id.recycle_budget)
    RecyclerView recycleBudget ;
    @BindView(R.id.rel_empty)
    RelativeLayout relEmpty ;

    private PageItemBean pageItemBean ;
    private List<DimensionPositionBean.IndexPositionBean> indexPositionBeans = new ArrayList<>() ;
    private BudgetIndexAdapter adapter ;

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
        initAdapter();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        GridLayoutManager manager = new GridLayoutManager(mContext,2);
        recycleBudget.setLayoutManager(manager);
        adapter = new BudgetIndexAdapter(mContext,indexPositionBeans,this);
        recycleBudget.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        if(pageItemBean == null){
            return;
        }

        HashMap params = new HashMap() ;
        params.put("time",pageItemBean.getTimeVal());
        if(!TextUtils.isEmpty(pageItemBean.getmOrgDimension())){
            params.put("orgDimension",pageItemBean.getmOrgDimension());
        }
        if(!TextUtils.isEmpty(pageItemBean.getmFuDimension())){
            params.put("fuDimension",pageItemBean.getmFuDimension());
        }
        if(!TextUtils.isEmpty(pageItemBean.getmPDimension())){
            params.put("pDimension",pageItemBean.getmPDimension());
        }
        getPresenter().getBudget(params);
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

    public static BudgetFragment newInstance(PageItemBean pageItemBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,pageItemBean);
        BudgetFragment fragment = new BudgetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 绩效数据获取成功
     * @param indexPositionForBudget
     */
    @Override
    public void getBudgetSuccess(List<DimensionPositionBean.IndexPositionBean> indexPositionForBudget) {
        this.indexPositionBeans.clear();
        this.indexPositionBeans.addAll(indexPositionForBudget);
        sortChart();
        if(indexPositionForBudget.isEmpty()){
            adapter.notifyDataSetChanged();
            relEmpty.setVisibility(View.VISIBLE);
        }else{
            relEmpty.setVisibility(View.GONE);
            for (DimensionPositionBean.IndexPositionBean indexPositionBean : indexPositionForBudget) {
                HashMap params = new HashMap() ;
                params.put("lang", LanguageUtils.isZh(mContext) ?"zh" : "en");
                params.put("timeValue",pageItemBean.getTimeVal());
                List arr = new ArrayList();
                if(!TextUtils.isEmpty(pageItemBean.getmOrgDimension())){
                    arr.add(pageItemBean.getmOrgDimension());
                }
                if(!TextUtils.isEmpty(pageItemBean.getmFuDimension())){
                    arr.add(pageItemBean.getmFuDimension()) ;
                }
                if(!TextUtils.isEmpty(pageItemBean.getmPDimension())){
                    arr.add(pageItemBean.getmPDimension()) ;
                }
                params.put("dimensionArr",arr);
                params.put("page",pageItemBean.getPage());
                List<ChatTypeRequestBean.ChartTypeBean> beans = new ArrayList<>();
                ChatTypeRequestBean.ChartTypeBean chartTypeBean = new ChatTypeRequestBean.ChartTypeBean() ;
                chartTypeBean.setIndexId(indexPositionBean.getIndex_id());
                chartTypeBean.setDataType(indexPositionBean.getChart_type());
                chartTypeBean.setAnalysisDim(indexPositionBean.getAnalysis_dimension());
                beans.add(chartTypeBean);
                params.put("chartType",beans);
                getPresenter().getEChart(params);
            }
        }
    }

    private void sortChart() {
        int indexId = -1 ;
        int positionId = -1 ;
        for (int i = 0 ; i < indexPositionBeans.size() ; i++){
            if(indexPositionBeans.get(i).getSize_x().equals("1")
                    && indexPositionBeans.get(i).getSize_y().equals("1")){
                /**
                 * 保存有空缺位置，讲此1*1调整到该位置，否则，保存此空缺位置
                 */
                DimensionPositionBean.IndexPositionBean bean = indexPositionBeans.get(i);
                if(indexId != -1){
                    indexPositionBeans.remove(bean);
                    indexPositionBeans.add(indexId,bean);
                    indexId = -1 ;
                    positionId = -1 ;
                }else{
                    positionId = i ;
                    indexId = i + 1;
                }
            }
        }
        if(positionId != -1){
            DimensionPositionBean.IndexPositionBean bean = indexPositionBeans.get(positionId);
            indexPositionBeans.remove(positionId);
            indexPositionBeans.add(bean);
        }
    }

    @Override
    public void getChartSuccess(IndexChartBean data) {
        for (DimensionPositionBean.IndexPositionBean indexPositionBean : indexPositionBeans) {
            if(TextUtils.isEmpty(indexPositionBean.getPage_chart_type())){
                indexPositionBean.setPage_chart_type(TextUtils.isEmpty(indexPositionBean.getPage_chart_type())
                        ? indexPositionBean.getChart_type().split(",")[0]
                        : indexPositionBean.getPage_chart_type());
            }
            if(indexPositionBean.getIndex_id().equals(data.getIndex_id())){
                if(TextUtils.isEmpty(indexPositionBean.getPage_chart_type())){
                    indexPositionBean.setPage_chart_type(TextUtils.isEmpty(data.getPage_chart_type())
                            ? data.getChart_type().split(",")[0]
                            : data.getPage_chart_type());
                }
                indexPositionBean.setChartBean(data);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnTitleClick(DimensionPositionBean.IndexPositionBean bean) {

    }

    @Override
    public void OnItemClick(DimensionPositionBean.IndexPositionBean bean) {
        IndexTreeNeedBean indexTreeNeedBean = new IndexTreeNeedBean();
        indexTreeNeedBean.setAnalysisDimension(bean.getAnalysis_dimension());
        indexTreeNeedBean.setOrgDimension(pageItemBean.getmOrgDimension());
        indexTreeNeedBean.setFuName(pageItemBean.getmFuName());
        indexTreeNeedBean.setpDimension(pageItemBean.getmPDimension());
        indexTreeNeedBean.setIndexId(bean.getIndex_id());
        indexTreeNeedBean.setOrgValue(pageItemBean.getmOrgValue());
        indexTreeNeedBean.setpName(pageItemBean.getMpName());
        indexTreeNeedBean.setFuValue(pageItemBean.getmFuValue());
        indexTreeNeedBean.setFuDimension(pageItemBean.getmFuDimension());
        indexTreeNeedBean.setpValue(pageItemBean.getMpValue());
        indexTreeNeedBean.setOrgName(pageItemBean.getmOrgName());
        indexTreeNeedBean.setTimeVal(pageItemBean.getTimeVal());
        indexTreeNeedBean.setType("4");
        Intent intent = new Intent(mContext, IndexTreeActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,indexTreeNeedBean);
        startActivity(intent);
    }

    @Override
    public void OnIndexDeleteClick(DimensionPositionBean.IndexPositionBean bean) {

    }
}
