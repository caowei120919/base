package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SelectAreaDimensionAdapter;
import com.datacvg.dimp.adapter.SelectOrgDimensionAdapter;
import com.datacvg.dimp.adapter.SelectProDimensionAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.event.FilterEvent;
import com.datacvg.dimp.presenter.SelectFilterPresenter;
import com.datacvg.dimp.view.SelectFilterView;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-19
 * @Description : 维度时间选择
 */
public class SelectFilterActivity extends BaseActivity<SelectFilterView, SelectFilterPresenter>
        implements SelectFilterView, SelectOrgDimensionAdapter.SelectOrgClickListener, SelectAreaDimensionAdapter.SelectAreaDimensionClickListener, SelectProDimensionAdapter.SelectProClickListener {
    @BindView(R.id.tv_parameterTime)
    TextView tvParameterTime ;
    @BindView(R.id.img_time)
    ImageView imgTime ;
    @BindView(R.id.line_org)
    LinearLayout lineOrg ;
    @BindView(R.id.tv_parameterOrg)
    TextView tvParameterOrg ;
    @BindView(R.id.img_org)
    ImageView imgOrg ;
    @BindView(R.id.rel_org)
    RelativeLayout relOrg ;
    @BindView(R.id.recycleOrg)
    RecyclerView recycleOrg ;
    @BindView(R.id.lin_area)
    LinearLayout linArea ;
    @BindView(R.id.tv_parameterArea)
    TextView tvParameterArea ;
    @BindView(R.id.img_area)
    ImageView imgArea ;
    @BindView(R.id.rel_area)
    RelativeLayout relArea ;
    @BindView(R.id.recycleArea)
    RecyclerView recycleArea ;
    @BindView(R.id.lin_pro)
    LinearLayout linPro ;
    @BindView(R.id.tv_parameterPro)
    TextView tvParameterPro ;
    @BindView(R.id.img_pro)
    ImageView imgPro ;
    @BindView(R.id.rel_pro)
    RelativeLayout relPro ;
    @BindView(R.id.recyclePro)
    RecyclerView recyclePro ;
    @BindView(R.id.timepicker)
    LinearLayout timePicker ;

    private final static String ORG_TAG = "ORG" ;
    private final static String AREA_TAG = "AREA" ;
    private final static String PRO_TAG = "PRO" ;
    private PageItemBean pageItemBean ;
    private TimePickerView pvCustomTime ;
    private List<DimensionBean> orgDimensions = new ArrayList<>() ;
    private List<DimensionBean> proDimensions = new ArrayList<>() ;
    private List<DimensionBean> areaDimensions = new ArrayList<>() ;
    private SelectOrgDimensionAdapter orgDimensionAdapter ;
    private SelectAreaDimensionAdapter areaDimensionAdapter ;
    private SelectProDimensionAdapter proDimensionAdapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_filter;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        initAdapter();
    }

    /**
     * 初始化维度选择器
     */
    private void initAdapter() {
        orgDimensionAdapter = new SelectOrgDimensionAdapter(mContext,orgDimensions,this);
        LinearLayoutManager orgManager = new LinearLayoutManager(mContext);
        recycleOrg.setLayoutManager(orgManager);
        recycleOrg.setAdapter(orgDimensionAdapter);

        areaDimensionAdapter = new SelectAreaDimensionAdapter(mContext,areaDimensions,this);
        LinearLayoutManager areaManager = new LinearLayoutManager(mContext);
        recycleArea.setLayoutManager(areaManager);
        recycleArea.setAdapter(areaDimensionAdapter);

        proDimensionAdapter = new SelectProDimensionAdapter(mContext,proDimensions,this);
        LinearLayoutManager proManager = new LinearLayoutManager(mContext);
        recyclePro.setLayoutManager(proManager);
        recyclePro.setAdapter(proDimensionAdapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        pageItemBean = (PageItemBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        PLog.e(pageItemBean.getPad_name());
        StringBuilder builder = new StringBuilder(pageItemBean.getTimeVal());
        switch (pageItemBean.getTime_type()){
            case "year":
                initCustomPickView(true,false,false);
                break;
            case "month":
                initCustomPickView(true,true,false);
                builder.insert(4,"-");
                break;
            default:
                initCustomPickView(true,true,true);
                builder.insert(6,"-").insert(4,"-");
                break;
        }
        tvParameterTime.setText(builder);
        lineOrg.setVisibility(TextUtils.isEmpty(pageItemBean.getmOrgName()) ? View.GONE : View.VISIBLE);
        tvParameterOrg.setText(TextUtils.isEmpty(pageItemBean.getmOrgName())?"":pageItemBean.getmOrgName());
        linPro.setVisibility(TextUtils.isEmpty(pageItemBean.getMpName()) ? View.GONE : View.VISIBLE);
        tvParameterPro.setText(TextUtils.isEmpty(pageItemBean.getMpName())?"":pageItemBean.getMpName());
        linArea.setVisibility(TextUtils.isEmpty(pageItemBean.getmFuName()) ? View.GONE : View.VISIBLE);
        tvParameterArea.setText(TextUtils.isEmpty(pageItemBean.getmFuName())?"":pageItemBean.getmFuName());

        if(!TextUtils.isEmpty(pageItemBean.getmOrgDimension())){
            List orgArr = new ArrayList() ;
            orgArr.add(pageItemBean.getmOrgDimension());
            Map paramOfOrg = new HashMap();
            paramOfOrg.put("timeVal",pageItemBean.getTimeVal());
            paramOfOrg.put("dimensionArr",orgArr);
            getPresenter().getDimension(paramOfOrg);
        }
    }

    /**
     *
     * @param hasYear
     * @param hasMonth
     * @param hasDay
     */
    private void initCustomPickView(boolean hasYear, boolean hasMonth, boolean hasDay) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1970,1,1);
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)
                , endDate.get(Calendar.DATE));
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
            }
        })
                .setType(new boolean[]{hasYear, hasMonth, hasDay, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time_of_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        switch (pageItemBean.getTime_type()){
                            case "year" :
                                tvParameterTime.setText(calendar.get(Calendar.YEAR)+"");
                                pageItemBean.setTimeVal(calendar.get(Calendar.YEAR) + "");
                                break;

                            case "month" :
                                tvParameterTime.setText(calendar.get(Calendar.YEAR) + "-"
                                        + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1)));
                                pageItemBean.setTimeVal(calendar.get(Calendar.YEAR) + ""+ ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1)));
                                break;

                            default:
                                tvParameterTime.setText(calendar.get(Calendar.YEAR) + "-"
                                        + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1))
                                        + "-" + (calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : "0" + calendar.get(Calendar.DATE)));
                                pageItemBean.setTimeVal(calendar.get                              (Calendar.YEAR) + ""
                                        + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1))
                                        + "" + (calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : "0" + calendar.get(Calendar.DATE)));
                                break;
                        }
                    }
                })
                .setContentTextSize(18)
                .setTitleText("")
                .setOutSideCancelable(false)
                .isCyclic(false)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .setDecorView(timePicker)
                .isDialog(false)
                .build();
        pvCustomTime.show();
    }

    @OnClick({R.id.tv_filtrate_cancel,R.id.tv_complete,R.id.lin_time,R.id.line_org,R.id.lin_area,R.id.lin_pro})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_filtrate_cancel :
                    finish();
                break;

            case R.id.tv_complete :
                PLog.e("确定");
                EventBus.getDefault().post(new FilterEvent(pageItemBean));
                finish();
                break;

            case R.id.lin_time :
                timePicker.setVisibility(timePicker.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                imgTime.setSelected(timePicker.getVisibility() == View.VISIBLE);
                break;
            case R.id.line_org :
                relOrg.setVisibility(relOrg.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                imgOrg.setSelected(!imgOrg.isSelected());
                break;

            case R.id.lin_area :
                relArea.setVisibility(relArea.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                imgArea.setSelected(!imgArea.isSelected());
                break;

            case R.id.lin_pro :
                relPro.setVisibility(relPro.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                imgPro.setSelected(!imgPro.isSelected());
                break;
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

    private void setPageItemBeanDimension(List<DimensionBean> dimensions, String tag) {
        switch (tag){
            case ORG_TAG :
                orgDimensions.clear();
                orgDimensions.addAll(dimensions);
                PLog.e(new Gson().toJson(dimensions));
                orgDimensionAdapter.notifyDataSetChanged();
                break;

            case AREA_TAG :
                areaDimensions.clear();
                areaDimensions.addAll(dimensions);
                PLog.e(new Gson().toJson(dimensions));
                areaDimensionAdapter.notifyDataSetChanged();
                break;

            case PRO_TAG :
                proDimensions.clear();
                proDimensions.addAll(dimensions);
                PLog.e(new Gson().toJson(dimensions));
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
                proDimensionAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * org维度选择
     * @param bean
     */
    @Override
    public void onSelectOrgClick(DimensionBean bean) {
        pageItemBean.setmOrgName(bean.getD_res_name());
        pageItemBean.setmOrgValue(bean.getD_res_value());
        pageItemBean.setmOrgDimension(bean.getId());
        tvParameterOrg.setText(bean.getD_res_name());
        relOrg.setVisibility(View.GONE);
        imgOrg.setSelected(!imgOrg.isSelected());
        relOrg.setTag(bean);
    }

    /**
     * area维度选择
     * @param bean
     */
    @Override
    public void onSelectAreaClick(DimensionBean bean) {
        pageItemBean.setmFuValue(bean.getD_res_value());
        pageItemBean.setmFuName(bean.getD_res_name());
        pageItemBean.setmFuDimension(bean.getId());
        tvParameterArea.setText(bean.getD_res_name());
        relArea.setVisibility(View.GONE);
        imgArea.setSelected(!imgArea.isSelected());
        relArea.setTag(bean);
    }

    /**
     * pro维度选择
     * @param bean
     */
    @Override
    public void onSelectProClick(DimensionBean bean) {
        pageItemBean.setMpValue(bean.getD_res_value());
        pageItemBean.setMpName(bean.getD_res_name());
        pageItemBean.setmPDimension(bean.getId());
        tvParameterPro.setText(bean.getD_res_name());
        relPro.setVisibility(View.GONE);
        imgPro.setSelected(!imgPro.isSelected());
        relPro.setTag(bean);
    }
}
