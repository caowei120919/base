package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.AddIndexActivity;
import com.datacvg.dimp.activity.AddIndexPageActivity;
import com.datacvg.dimp.activity.SelectFilterActivity;
import com.datacvg.dimp.adapter.DimensionIndexAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ChangeChartRequestBean;
import com.datacvg.dimp.bean.ChatTypeRequestBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.event.AddIndexEvent;
import com.datacvg.dimp.event.CheckIndexEvent;
import com.datacvg.dimp.event.CompleteEvent;
import com.datacvg.dimp.event.DeletePageEvent;
import com.datacvg.dimp.event.EditEvent;
import com.datacvg.dimp.event.PageCompleteEvent;
import com.datacvg.dimp.event.SavePageEvent;
import com.datacvg.dimp.event.SelectParamsEvent;
import com.datacvg.dimp.event.ToAddIndexEvent;
import com.datacvg.dimp.presenter.BoardPagerPresenter;
import com.datacvg.dimp.view.BoardPagerView;
import com.enlogy.statusview.StatusRelativeLayout;
import com.google.gson.Gson;
import com.mylhyl.superdialog.SuperDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class BoardPagerFragment extends BaseFragment<BoardPagerView, BoardPagerPresenter>
        implements BoardPagerView {

    @BindView(R.id.tv_pageName)
    TextView tvPageName ;
    @BindView(R.id.tv_pageTime)
    TextView tvPageTime ;
    @BindView(R.id.tv_timeType)
    TextView tvTimeType ;
    @BindView(R.id.status_board)
    StatusRelativeLayout statusBoard ;
    @BindView(R.id.recycle_index)
    RecyclerView recycleIndex ;
    @BindView(R.id.rel_addOrDelete)
    RelativeLayout relAddOrDelete ;

    private final static String ORG_TAG = "ORG" ;
    private final static String AREA_TAG = "AREA" ;
    private final static String PRO_TAG = "PRO" ;
    private PageItemBean pageItemBean ;
    private List<DimensionPositionBean.IndexPositionBean> indexPositionBeans = new ArrayList<>() ;
    private DimensionIndexAdapter adapter ;
    private Boolean isComplete = false ;

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
        pageItemBean = (PageItemBean) getArguments().getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
        setTimeValue();
        initChartAdapter() ;
    }

    private void initChartAdapter() {
        GridLayoutManager manager = new GridLayoutManager(mContext,2);
        recycleIndex.setLayoutManager(manager);
        adapter = new DimensionIndexAdapter(mContext,indexPositionBeans);
        recycleIndex.setAdapter(adapter);
    }

    private void setTimeValue() {
        tvTimeType.setVisibility(View.VISIBLE);
        switch (pageItemBean.getTime_type()){
            case "month" :
                tvPageTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH,""));
                tvTimeType.setText(resources.getString(R.string.month));
                break;
            case "year" :
                tvPageTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_YEAR,""));
                tvTimeType.setText(resources.getString(R.string.year));
                break;
            default:
                tvPageTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_DAY,""));
                tvTimeType.setText(resources.getString(R.string.day));
                break;
        }
        pageItemBean.setTimeVal(tvPageTime.getText().toString()
                .replaceAll("/",""));
        if(pageItemBean.getPad_name().contains("{default}")){
            tvPageName.setText(resources.getString(R.string.the_current_page));
        }else {
            tvPageName.setText(resources.getString(R.string.the_current_page)
                    + pageItemBean.getPad_name());
        }
    }


    @Override
    protected void setupData() {
        if(!TextUtils.isEmpty(pageItemBean.getmOrgDimension())){
            List orgArr = new ArrayList() ;
            orgArr.add(pageItemBean.getmOrgDimension());
            Map paramOfOrg = new HashMap();
            paramOfOrg.put("timeVal",pageItemBean.getTimeVal());
            paramOfOrg.put("dimensionArr",orgArr);
            getPresenter().getDimension(paramOfOrg);
        }

        Map positionMap = new HashMap() ;
        positionMap.put("page",pageItemBean.getPage());
        getPresenter().getPosition(positionMap);
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
            chartTypeBean.setDataType(indexPositionBean.getPage_chart_type());
            chartTypeBean.setAnalysisDim(indexPositionBean.getAnalysis_dimension());
            beans.add(chartTypeBean);
            params.put("chartType",beans);
            getPresenter().getEChart(params);
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
    public void getDimensionSuccess(List<DimensionBean> selectDimension) {
        PLog.e(new Gson().toJson(selectDimension));
        setPageItemBeanDimension(selectDimension,ORG_TAG);
        if(!TextUtils.isEmpty(pageItemBean.getmFuDimension())){
            List fuArr = new ArrayList() ;
            fuArr.add(pageItemBean.getmOrgDimension());
            fuArr.add(pageItemBean.getmFuDimension());
            Map paramOfFu = new HashMap();
            paramOfFu.put("timeVal",pageItemBean.getTimeVal());
            paramOfFu.put("dimensionArr",fuArr);
            getPresenter().getOtherDimension(paramOfFu,PRO_TAG);
        }
    }

    @Override
    public void getOtherDimensionSuccess(List<DimensionBean> selectOtherDimension, String tag) {
        PLog.e(new Gson().toJson(selectOtherDimension));
        setPageItemBeanDimension(selectOtherDimension,tag);
    }

    /**
     * 页码删除成功
     */
    @Override
    public void deletePageSuccess() {
        ToastUtils.showLongToast(resources.getString(R.string.delete_the_success));
        EventBus.getDefault().post(new DeletePageEvent());
    }

    /**
     * 页面保存成功
     */
    @Override
    public void savePageSuccess() {
        String padName = ((EditText)statusBoard.findViewById(R.id.edit_pageName)).getText().toString();
        pageItemBean.setPad_name(padName);
        ToastUtils.showLongToast(resources.getString(R.string.save_success));
        if(isComplete){
            PLog.e("完成");
            EventBus.getDefault().post(new PageCompleteEvent());
            relAddOrDelete.setVisibility(View.GONE);
            statusBoard.showContent();
            tvPageName.setText(resources.getString(R.string.the_current_page)
                    + padName);
        }else{
            Intent intent = new Intent(mContext, AddIndexPageActivity.class);
            intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,pageItemBean.getPage());
            mContext.startActivity(intent);
        }
    }

    @OnClick({R.id.lin_deletePage,R.id.lin_addPage})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.lin_deletePage :
                PLog.e("删除页");
                showDeleteDialog(resources.getString(R.string.are_you_sure_to_delete_all_data_on_this_page_and_current_page));
                break;

            case R.id.lin_addPage :
                if(!isFragmentVisible()){
                    return;
                }
                PLog.e("新增页");
                if(indexPositionBeans.isEmpty()){
                    showDeleteDialog(resources.getString(R.string.this_page_is_empty_after_deleting_the_data_and_will_be_deleted_here));
                }else{
                    isComplete = false ;
                    String padName = ((EditText)statusBoard.findViewById(R.id.edit_pageName)).getText().toString() ;
                    EventBus.getDefault().post(new CheckPageNameEvent(padName));
                }
                break;
        }
    }

    /**
     * 保存页面指标信息
     */
    private void savePageIndex() {
        String padName = ((EditText)statusBoard.findViewById(R.id.edit_pageName)).getText().toString();
        if(TextUtils.isEmpty(padName)){
            ToastUtils.showLongToast(resources.getString(R.string.kanban_name_cannot_be_set_to_blank));
            return;
        }
        ChangeChartRequestBean changeChartRequestBean = new ChangeChartRequestBean() ;
        changeChartRequestBean.setPad_name(padName);
        changeChartRequestBean.setPad_number(pageItemBean.getPage());
        List<ChangeChartRequestBean.BisysindexpositionBean> bisysindexpositionBeans = new ArrayList<>() ;
        for (DimensionPositionBean.IndexPositionBean bean : indexPositionBeans){
            ChangeChartRequestBean.BisysindexpositionBean bisysindexpositionBean
                    = new ChangeChartRequestBean.BisysindexpositionBean();
            bisysindexpositionBean.setIndex_id(bean.getIndex_id());
            bisysindexpositionBean.setPage(pageItemBean.getPage());
            bisysindexpositionBean.setPos_x(bean.getPos_x()+"");
            bisysindexpositionBean.setPos_y(bean.getPos_y()+"");
            bisysindexpositionBean.setSize_x(bean.getSize_x());
            bisysindexpositionBean.setSize_y(bean.getSize_y());
            bisysindexpositionBeans.add(bisysindexpositionBean);
        }
        changeChartRequestBean.setBisysindexposition(bisysindexpositionBeans);
        getPresenter().changeChart(new Gson().fromJson(new Gson()
                .toJson(changeChartRequestBean),HashMap.class));
    }


    /**
     * 删除页提示
     */
    private void showDeleteDialog(String message) {
        List<String> listButton = new ArrayList<>();
        listButton.add(resources.getString(R.string.confirm_the_deletion));
        new SuperDialog.Builder(getActivity())
                .setCanceledOnTouchOutside(false)
                .setTitle(message,resources.getColor(R.color.c_303030),24,80)
                .setItems(listButton,resources.getColor(R.color.c_da3a16),24,80
                        , new SuperDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        deletePage();
                    }
                })
                .setNegativeButton(resources.getString(R.string.cancel)
                        ,resources.getColor(R.color.c_303030),24,80, null)
                .build();
    }

    /**
     * 删除页
     */
    private void deletePage() {
        getPresenter().deletePage(pageItemBean.getPage());
    }

    /**
     * 设置pageItemBean相关维度信息
     * @param dimensions
     * @param tag
     */
    private void setPageItemBeanDimension(List<DimensionBean> dimensions, String tag) {
        String tagDimension = "" ;
        switch (tag){
            case ORG_TAG :
                tagDimension = pageItemBean.getmOrgDimension() ;
                break;

            case AREA_TAG :
                tagDimension = pageItemBean.getmFuDimension() ;
                if(!TextUtils.isEmpty(pageItemBean.getmPDimension())){
                    List pArr = new ArrayList() ;
                    pArr.add(pageItemBean.getmOrgDimension());
                    pArr.add(pageItemBean.getmFuDimension());
                    pArr.add(pageItemBean.getmPDimension());
                    Map paramOfFu = new HashMap();
                    paramOfFu.put("timeVal",pageItemBean.getTimeVal());
                    paramOfFu.put("dimensionArr",pArr);
                    getPresenter().getOtherDimension(paramOfFu,AREA_TAG);
                }
                break;

            case PRO_TAG :
                tagDimension = pageItemBean.getmPDimension() ;
                break;
        }
        for (DimensionBean bean:dimensions){
            if(bean.getId().equals(tagDimension)){
                switch (tag){
                    case ORG_TAG :
                        pageItemBean.setmOrgValue(bean.getD_res_value());
                        pageItemBean.setmOrgName(bean.getD_res_name());
                        break;

                    case AREA_TAG :
                        pageItemBean.setmFuName(bean.getD_res_name());
                        pageItemBean.setmFuValue(bean.getD_res_value());
                        break;

                    case PRO_TAG :
                        pageItemBean.setMpValue(bean.getD_res_value());
                        pageItemBean.setMpName(bean.getD_res_name());
                        break;
                }
                return;
            }
            if(bean.getNodes() != null && bean.getNodes().size() > 0){
                setPageItemBeanDimension(bean.getNodes(),tag);
            }
        }
    }

    /**
     * 完成操作
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CompleteEvent event){
        isComplete = true ;
        if(isFragmentVisible()){
            savePageIndex();
        }
    }

    /**
     * 编辑状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EditEvent event){
        relAddOrDelete.setVisibility(View.VISIBLE);
        statusBoard.showExtendContent();
        ((EditText)statusBoard.findViewById(R.id.edit_pageName)).setText(pageItemBean.getPad_name());
    }

    /**
     * 看板保存校验成功
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SavePageEvent event){
        savePageIndex();
    }

    /**
     * 修改指标
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckIndexEvent event){
        if(event.getPageItemBean() != pageItemBean){
            return;
        }
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
        eChartListBean.setPageNo(pageItemBean.getPage());
        eChartListBean.setPageName(pageItemBean.getPad_name());
        eChartListBean.setIndexChart(indexChartBeans);
        Intent intent = new Intent(mContext, AddIndexActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,eChartListBean);
        mContext.startActivity(intent);
    }

    /**
     * 指标修改后
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddIndexEvent event){
        if (!event.getPageNo().equals(pageItemBean.getPage())){
            return;
        }
        indexPositionBeans.clear();
        indexPositionBeans.addAll(event.getDimensionPositionBean().getIndexPosition());
        sortChart();
        for (DimensionPositionBean.IndexPositionBean indexPositionBean : indexPositionBeans) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PageCompleteEvent event){
        if(!isFragmentVisible()){
            relAddOrDelete.setVisibility(View.GONE);
            statusBoard.showContent();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectParamsEvent event){
        if(isFragmentVisible()){
            Intent intent = new Intent(mContext, SelectFilterActivity.class);
            intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,pageItemBean);
            mContext.startActivity(intent);
        }
    }
}
