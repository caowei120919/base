package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.AddIndexActivity;
import com.datacvg.dimp.activity.ChartDetailActivity;
import com.datacvg.dimp.activity.IndexTreeActivity;
import com.datacvg.dimp.adapter.DimensionIndexAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ChangeChartRequestBean;
import com.datacvg.dimp.bean.ChatTypeRequestBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.event.AddIndexEvent;
import com.datacvg.dimp.event.ChangePageChartEvent;
import com.datacvg.dimp.event.DeletePageEvent;
import com.datacvg.dimp.event.RefreshEvent;
import com.datacvg.dimp.event.ShakeEvent;
import com.datacvg.dimp.event.ToAddIndexEvent;
import com.datacvg.dimp.presenter.BoardPagerPresenter;
import com.datacvg.dimp.view.BoardPagerView;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class BoardPagerFragment extends BaseFragment<BoardPagerView, BoardPagerPresenter>
        implements BoardPagerView ,DimensionIndexAdapter.IndexClickListener{
    @BindView(R.id.recycle_index)
    RecyclerView recycleIndex ;

    private final String ORG_TAG = "ORG" ;
    private final String AREA_TAG = "AREA" ;
    private final String PRO_TAG = "PRO" ;
    private PageItemBean itemBean ;
    private DimensionIndexAdapter adapter ;
    private List<DimensionPositionBean.IndexPositionBean> indexPositionBeans = new ArrayList<>() ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_board_pager;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        initAdapter();
    }


    private void initAdapter() {
        GridLayoutManager manager = new GridLayoutManager(mContext,2);
        recycleIndex.setLayoutManager(manager);
        adapter = new DimensionIndexAdapter(mContext,indexPositionBeans,this);
        recycleIndex.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        setItemBean();
        getDimensionValue();
        getPageData();
    }

    /**
     * 获取维度信息
     */
    private void getDimensionValue() {
        if (TextUtils.isEmpty(itemBean.getmOrgDimension())){
            return;
        }
        List arr = new ArrayList();
        arr.add(itemBean.getmOrgDimension());
        Map params = new HashMap();
        params.put("timeVal",itemBean.getTimeVal());
        params.put("dimensionArr",arr);
        getPresenter().getDimension(params);

        if(TextUtils.isEmpty(itemBean.getmFuDimension())){
            return;
        }
        arr.add(itemBean.getmFuDimension());
        params.put("dimensionArr",arr);
        getPresenter().getOtherDimension(params,AREA_TAG);

        if(TextUtils.isEmpty(itemBean.getmPDimension())){
            return;
        }
        arr.add(itemBean.getmPDimension());
        params.put("dimensionArr",arr);
        getPresenter().getOtherDimension(params,PRO_TAG);
    }

    private void getPageData() {
        Map params = new HashMap();
        params.put("page",itemBean.getPage());
        getPresenter().getPosition(params);
    }

    private void setItemBean() {
        itemBean = (PageItemBean) getArguments().getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
        adapter.setHolderShake(itemBean.getShake());
        if(TextUtils.isEmpty(itemBean.getTimeVal())){
            switch (itemBean.getTime_type()){
                case "month" :
                    itemBean.setTimeVal(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH,"")
                            .replaceAll("/",""));
                    break;
                case "year" :
                    itemBean.setTimeVal(PreferencesHelper.get(Constants.USER_DEFAULT_YEAR,"")
                            .replaceAll("/",""));
                    break;
                default:
                    itemBean.setTimeVal(PreferencesHelper.get(Constants.USER_DEFAULT_DAY,"")
                            .replaceAll("/",""));
                    break;
            }
        }
        itemBean.setTimeVal(PreferencesHelper.get(Constants.USER_DEFAULT_TIME,""));
    }

    public static BoardPagerFragment newInstance(PageItemBean bean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,bean);
        BoardPagerFragment fragment = new BoardPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getIndexPositionSuccess(List<DimensionPositionBean.IndexPositionBean> indexPosition) {
        this.indexPositionBeans.clear();
        this.indexPositionBeans.addAll(indexPosition);
        sortChart();
        for (DimensionPositionBean.IndexPositionBean indexPositionBean : indexPosition) {
            HashMap params = new HashMap() ;
            params.put("lang", LanguageUtils.isZh(mContext) ?"zh" : "en");
            params.put("timeValue",itemBean.getTimeVal());
            List arr = new ArrayList();
            if(!TextUtils.isEmpty(itemBean.getmOrgDimension())){
                arr.add(itemBean.getmOrgDimension());
            }
            if(!TextUtils.isEmpty(itemBean.getmFuDimension())){
                arr.add(itemBean.getmFuDimension()) ;
            }
            if(!TextUtils.isEmpty(itemBean.getmPDimension())){
                arr.add(itemBean.getmPDimension()) ;
            }
            params.put("dimensionArr",arr);
            params.put("page",itemBean.getPage());
            List<ChatTypeRequestBean.ChartTypeBean> beans = new ArrayList<>();
            ChatTypeRequestBean.ChartTypeBean chartTypeBean = new ChatTypeRequestBean.ChartTypeBean() ;
            chartTypeBean.setIndexId(indexPositionBean.getIndex_id());
            chartTypeBean.setDataType(indexPositionBean.getPage_chart_type());
            chartTypeBean.setAnalysisDim(indexPositionBean.getAnalysis_dimension());
            beans.add(chartTypeBean);
            params.put("chartType",beans);
            getChartData(params);
        }
    }

    /**
     * 图表信息获取成功
     * @param data
     */
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
    public void getDimensionSuccess(List<DimensionBean> selectDimension) {
        setItemBeanDimension(selectDimension,ORG_TAG);
    }

    @Override
    public void getOtherDimensionSuccess(List<DimensionBean> selectOtherDimension, String tag) {
        setItemBeanDimension(selectOtherDimension,tag);
    }

    /**
     * 设置维度value
     * @param dimensions
     * @param tag
     */
    private void setItemBeanDimension(List<DimensionBean> dimensions, String tag) {
        String tagDimension = "" ;
        switch (tag){
            case ORG_TAG :
                tagDimension = itemBean.getmOrgDimension() ;
                break;

            case AREA_TAG :
                tagDimension = itemBean.getmFuDimension() ;
                break;

            case PRO_TAG :
                tagDimension = itemBean.getmPDimension() ;
                break;
        }
        for (DimensionBean bean:dimensions){
            if(bean.getId().equals(tagDimension)){
                switch (tag){
                    case ORG_TAG :
                        itemBean.setmOrgValue(bean.getD_res_value());
                        itemBean.setmOrgName(bean.getD_res_name());
                        break;

                    case AREA_TAG :
                        itemBean.setmFuName(bean.getD_res_name());
                        itemBean.setmFuValue(bean.getD_res_value());
                        break;

                    case PRO_TAG :
                        itemBean.setMpValue(bean.getD_res_value());
                        itemBean.setMpName(bean.getD_res_name());
                        break;
                }
            return;
            }
            if(bean.getNodes() != null && bean.getNodes().size() > 0){
                setItemBeanDimension(bean.getNodes(),tag);
            }
        }
    }

    /**
     * 查询图表信息数据
     * @param params
     */
    private void getChartData(HashMap params) {
        getPresenter().getEChart(params);
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
    public void OnTitleClick(DimensionPositionBean.IndexPositionBean bean) {
        /**
         * orgName : 数聚股份
         * pValue : GOODS
         * fuDimension : 118306192070461277956
         * type : 4
         * fuValue : region
         * pName : 产品或服务
         * orgValue : DATACVG
         * analysisDimension : user_org
         * indexId : IBI-ex-cost_MONTH
         * pDimension : 118341583624371459776
         * fuName : 所有地区
         * timeVal : 202009
         * orgDimension : 14860367656855969470
         * state :
         */
        IndexTreeNeedBean indexTreeNeedBean = new IndexTreeNeedBean();
        indexTreeNeedBean.setOrgName(itemBean.getmOrgName());
        indexTreeNeedBean.setpValue(itemBean.getMpValue());
        indexTreeNeedBean.setFuDimension(itemBean.getmFuDimension());
        indexTreeNeedBean.setType("4");
        indexTreeNeedBean.setFuValue(itemBean.getmFuValue());
        indexTreeNeedBean.setpName(itemBean.getMpName());
        indexTreeNeedBean.setOrgValue(itemBean.getmOrgValue());
        indexTreeNeedBean.setAnalysisDimension(bean.getAnalysis_dimension());
        indexTreeNeedBean.setIndexId(bean.getIndex_id());
        indexTreeNeedBean.setpDimension(itemBean.getmPDimension());
        indexTreeNeedBean.setFuName(itemBean.getmFuName());
        indexTreeNeedBean.setTimeVal(itemBean.getTimeVal());
        indexTreeNeedBean.setOrgDimension(itemBean.getmOrgDimension());
        PLog.e(new Gson().toJson(indexTreeNeedBean));
        Intent intent = new Intent(mContext, IndexTreeActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,indexTreeNeedBean);
        startActivity(intent);
    }

    @Override
    public void OnItemClick(DimensionPositionBean.IndexPositionBean bean) {
        if(bean.getChartBean() == null){
            return;
        }
        bean.setTime_type(itemBean.getTime_type());
        Intent intent = new Intent(mContext, ChartDetailActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,itemBean);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,bean);
        mContext.startActivity(intent);
    }

    /**
     * 删除指标
     * @param bean
     */
    @Override
    public void OnIndexDeleteClick(DimensionPositionBean.IndexPositionBean bean) {
        if(indexPositionBeans.size() == 1){
            EventBus.getDefault().post(new DeletePageEvent(resources
                    .getString(R.string.this_page_is_empty_after_deleting_the_data_and_will_be_deleted_here)));
        }else{
            indexPositionBeans.remove(bean);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShakeEvent event){
        adapter.setHolderShake(event.isShake());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangePageChartEvent event){
        ChangeChartRequestBean changeChartRequestBean = new ChangeChartRequestBean() ;
        changeChartRequestBean.setPad_name(itemBean.getPad_name());
        changeChartRequestBean.setPad_number(itemBean.getPage());
        List<ChangeChartRequestBean.BisysindexpositionBean> bisysindexpositionBeans = new ArrayList<>() ;
        for (DimensionPositionBean.IndexPositionBean bean : indexPositionBeans){
            ChangeChartRequestBean.BisysindexpositionBean bisysindexpositionBean
                    = new ChangeChartRequestBean.BisysindexpositionBean();
            bisysindexpositionBean.setIndex_id(bean.getIndex_id());
            bisysindexpositionBean.setPage(itemBean.getPage());
            bisysindexpositionBean.setPos_x(bean.getPos_x()+"");
            bisysindexpositionBean.setPos_y(bean.getPos_y()+"");
            bisysindexpositionBean.setSize_x(bean.getSize_x());
            bisysindexpositionBean.setSize_y(bean.getSize_y());
            bisysindexpositionBeans.add(bisysindexpositionBean);
        }
        changeChartRequestBean.setBisysindexposition(bisysindexpositionBeans);
        getPresenter().changeChart(new Gson().fromJson(new Gson()
                .toJson(changeChartRequestBean),Map.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ToAddIndexEvent event){
        PageItemBean bean = event.getBean() ;
        if(bean != itemBean){
            return;
        }
        PLog.e(new Gson().toJson(indexPositionBeans));
        EChartListBean eChartListBean = new EChartListBean();
        List<IndexChartBean> indexChartBeans = new ArrayList<>();
        for (DimensionPositionBean.IndexPositionBean indexPositionBean :indexPositionBeans){
            if(indexPositionBean.getChartBean() != null){
                indexPositionBean.getChartBean()
                        .setAnalysis_dimension(indexPositionBean.getAnalysis_dimension());
                indexPositionBean.getChartBean().setPage_chart_type(indexPositionBean.getPage_chart_type());
                indexPositionBean.getChartBean().setChart_type(indexPositionBean.getChart_type());
                indexChartBeans.add(indexPositionBean.getChartBean()) ;
            }
        }
        eChartListBean.setPageNo(itemBean.getPage());
        eChartListBean.setPageName(itemBean.getPad_name());
        eChartListBean.setIndexChart(indexChartBeans);
        Intent intent = new Intent(mContext, AddIndexActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,eChartListBean);
        mContext.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddIndexEvent event){
        if(!itemBean.getPage().equals(event.getPageNo())){
            return;
        }
        this.indexPositionBeans.clear();
        this.indexPositionBeans.addAll(event.getDimensionPositionBean().getIndexPosition());
        sortChart();
        for (DimensionPositionBean.IndexPositionBean indexPositionBean : indexPositionBeans) {
            HashMap params = new HashMap() ;
            params.put("lang", LanguageUtils.isZh(mContext) ?"zh" : "en");
            params.put("timeValue",itemBean.getTimeVal());
            List arr = new ArrayList();
            if(!TextUtils.isEmpty(itemBean.getmOrgDimension())){
                arr.add(itemBean.getmOrgDimension());
            }
            if(!TextUtils.isEmpty(itemBean.getmFuDimension())){
                arr.add(itemBean.getmFuDimension()) ;
            }
            if(!TextUtils.isEmpty(itemBean.getmPDimension())){
                arr.add(itemBean.getmPDimension()) ;
            }
            params.put("dimensionArr",arr);
            params.put("page",itemBean.getPage());
            List<ChatTypeRequestBean.ChartTypeBean> beans = new ArrayList<>();
            ChatTypeRequestBean.ChartTypeBean chartTypeBean = new ChatTypeRequestBean.ChartTypeBean() ;
            chartTypeBean.setIndexId(indexPositionBean.getIndex_id());
            chartTypeBean.setDataType(TextUtils.isEmpty(indexPositionBean.getPage_chart_type())
                    ? indexPositionBean.getChart_type().split(",")[0]
                    : indexPositionBean.getPage_chart_type());
            chartTypeBean.setAnalysisDim(indexPositionBean.getAnalysis_dimension());
            beans.add(chartTypeBean);
            params.put("chartType",beans);
            getChartData(params);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent event){
        getPageData();
    }
}
