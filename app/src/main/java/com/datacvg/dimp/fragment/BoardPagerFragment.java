package com.datacvg.dimp.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.DimensionIndexAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ChatTypeRequestBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.presenter.BoardPagerPresenter;
import com.datacvg.dimp.view.BoardPagerView;

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

    private PageItemBean itemBean ;
    private DimensionIndexAdapter adapter ;
    private final String areaTag = "area" ;
    private final String proTag = "pro" ;
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
        int space = getResources().getDimensionPixelSize(R.dimen.H10);
        recycleIndex.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = space;
            }
        });
        recycleIndex.setLayoutManager(manager);
        adapter = new DimensionIndexAdapter(mContext,indexPositionBeans,this);
        recycleIndex.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        setItemBean();
    }

    private void setItemBean() {
        itemBean = (PageItemBean) getArguments().getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
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
        if(TextUtils.isEmpty(itemBean.getmOrgDimension())){
            PLog.e(itemBean.getDimensions());
            getDimension();
        }
    }

    /**
     * 获取第一维度信息
     */
    private void getDimension() {
        if(TextUtils.isEmpty(itemBean.getmOrgDimension())){
            getPresenter().getDimension(itemBean.getTimeVal());
        }
    }

    public static BoardPagerFragment newInstance(PageItemBean bean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,bean);
        BoardPagerFragment fragment = new BoardPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 第一维度获取成功
     * @param selectDimension
     */
    @Override
    public void getDimensionSuccess(List<DimensionBean> selectDimension) {
        if (selectDimension != null && selectDimension.size() > 0){
            itemBean.setmOrgDimension(selectDimension.get(0).getId());
            itemBean.setmOrgValue(selectDimension.get(0).getValue());
            if(TextUtils.isEmpty(itemBean.getmFuDimension())){
                Map params = new HashMap();
                params.put("timeVal",itemBean.getTimeVal());
                params.put("orgValue",itemBean.getmOrgDimension());
                getPresenter().getOtherDimension(params,areaTag);
            }
        }else{
            Map params = new HashMap();
            params.put("page",itemBean.getPage());
            getPresenter().getPosition(params);
        }
    }

    @Override
    public void getOtherDimensionSuccess(List<DimensionBean> selectOtherDimension, String tag) {
        switch (tag){
            case areaTag :
                if (selectOtherDimension != null && selectOtherDimension.size() > 0){
                    itemBean.setmFuDimension(selectOtherDimension.get(0).getId());
                    itemBean.setmFuValue(selectOtherDimension.get(0).getValue());
                    if(TextUtils.isEmpty(itemBean.getmPDimension())){
                        Map params = new HashMap();
                        params.put("timeVal",itemBean.getTimeVal());
                        params.put("orgValue",itemBean.getmOrgDimension());
                        params.put("fuValue",itemBean.getmFuDimension());
                        getPresenter().getOtherDimension(params,proTag);
                    }
                }else{
                    Map params = new HashMap();
                    params.put("page",itemBean.getPage());
                    getPresenter().getPosition(params);
                }
                break;

            case proTag :
                if (selectOtherDimension != null && selectOtherDimension.size() > 0){
                    itemBean.setmPDimension(selectOtherDimension.get(0).getId());
                    itemBean.setMpValue(selectOtherDimension.get(0).getValue());
                }else{
                    Map params = new HashMap();
                    params.put("page",itemBean.getPage());
                    getPresenter().getPosition(params);
                }
                break;
        }
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
        PLog.e(data.getIndex_id() + "=================== " + data.getIndex_pkid());
        for (DimensionPositionBean.IndexPositionBean indexPositionBean : indexPositionBeans) {
            if(indexPositionBean.getIndex_id().equals(data.getIndex_id())){
                indexPositionBean.setChartBean(data);
            }
        }
        adapter.notifyDataSetChanged();
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
    public void OnTitleClick(IndexChartBean bean) {

    }
}
