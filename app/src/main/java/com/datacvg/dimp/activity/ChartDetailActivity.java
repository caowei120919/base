package com.datacvg.dimp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.datacvg.dimp.AAChartCoreLib.AAChartCreator.AAChartView;
import com.datacvg.dimp.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartAlignType;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartAnimationType;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartLayoutType;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartType;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartVerticalAlignType;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAAnimation;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AABar;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AADataLabels;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAItemStyle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AALabels;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AALegend;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAOptions;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAPie;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAPlotOptions;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AASeries;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAStyle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AATitle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AATooltip;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAXAxis;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAYAxis;
import com.datacvg.dimp.AAChartCoreLib.AATools.AAColor;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SelectChartDimensionAdapter;
import com.datacvg.dimp.adapter.SelectChartTypeAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.ChartTypeBean;
import com.datacvg.dimp.bean.ChatTypeRequestBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.DimensionTypeBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.LinChartBaseBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.bean.PieChartBaseBean;
import com.datacvg.dimp.event.RefreshEvent;
import com.datacvg.dimp.presenter.ChartDetailPresenter;
import com.datacvg.dimp.view.ChartDetailView;
import com.datacvg.dimp.widget.BulletChart;
import com.datacvg.dimp.widget.DashboardChart;
import com.datacvg.dimp.widget.DialogForChartType;
import com.datacvg.dimp.widget.DialogForDimension;
import com.enlogy.statusview.StatusRelativeLayout;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class ChartDetailActivity extends BaseActivity<ChartDetailView, ChartDetailPresenter>
        implements ChartDetailView, SelectChartTypeAdapter.OnTypeClickListener, SelectChartDimensionAdapter.OnTypeClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_chartType)
    TextView tvChartType ;
    @BindView(R.id.tv_dimensionType)
    TextView tvDimensionType ;
    @BindView(R.id.tv_note)
    TextView tvNote ;
    @BindView(R.id.tv_target)
    TextView tvTarget ;
    @BindView(R.id.tv_guaranteed)
    TextView tvGuaranteed ;
    @BindView(R.id.tv_challenge)
    TextView tvChallenge ;
    @BindView(R.id.status_chart)
    StatusRelativeLayout statusChart ;

    private DimensionPositionBean.IndexPositionBean bean ;
    private List<ChartTypeBean> chartTypeBeans = new ArrayList<>() ;
    private List<DimensionTypeBean> dimensionTypeBeans = new ArrayList<>() ;
    private DialogForChartType dialogForChartType;
    private DialogForDimension dialogForDimension ;
    private TimePickerView pvCustomTime ;
    private PageItemBean itemBean ;
    private String chartType ;
    private String dimensionSelect ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chart_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        Drawable drawable = resources.getDrawable(R.mipmap.icon_calendar);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null,null,drawable,null);
        tvTitle.setCompoundDrawablePadding(40);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        bean = (DimensionPositionBean.IndexPositionBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        itemBean = (PageItemBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_SCAN);
        if(bean.getChartBean() == null){
            return;
        }
        switch (itemBean.getTime_type()){
            case "month" :
                initCustomPickView(true,true,false,TimeUtils.FORMAT_YM_EN);
                tvTitle.setText(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH,"")
                        .replaceAll("/","-"));
                break;
            case "year" :
                initCustomPickView(true,false,false,TimeUtils.FORMAT_Y);
                tvTitle.setText(PreferencesHelper.get(Constants.USER_DEFAULT_YEAR,"")
                        .replaceAll("/","-"));
                break;
            default:
                initCustomPickView(true,true,true,TimeUtils.FORMAT_YMD);
                tvTitle.setText(PreferencesHelper.get(Constants.USER_DEFAULT_DAY,"")
                        .replaceAll("/","-"));
                break;
        }
        setChartData(bean.getChartBean());
        createChartTypePopData(bean);
        createDimensionTypeData();
    }

    /**
     * 封装维度
     */
    private void createDimensionTypeData() {
        DimensionTypeBean dimensionTypeBean = new DimensionTypeBean();
        dimensionTypeBean.setName("时间维度");
        dimensionTypeBean.setDimensionId("TIME");
        dimensionTypeBeans.add(dimensionTypeBean);
        dimensionSelect = "TIME" ;

        if(!TextUtils.isEmpty(itemBean.getmOrgDimension())){
            DimensionTypeBean dimensionTypeBeanOfOrg = new DimensionTypeBean();
            dimensionTypeBeanOfOrg.setName(itemBean.getmOrgName());
            dimensionTypeBeanOfOrg.setDimensionId(itemBean.getmOrgDimension());
            dimensionTypeBeans.add(dimensionTypeBeanOfOrg);
        }

        if(!TextUtils.isEmpty(itemBean.getmFuDimension())){
            DimensionTypeBean dimensionTypeBeanOfOrg = new DimensionTypeBean();
            dimensionTypeBeanOfOrg.setName(itemBean.getmFuName());
            dimensionTypeBeanOfOrg.setDimensionId(itemBean.getmFuDimension());
            dimensionTypeBeans.add(dimensionTypeBeanOfOrg);
        }

        if(!TextUtils.isEmpty(itemBean.getmPDimension())){
            DimensionTypeBean dimensionTypeBeanOfOrg = new DimensionTypeBean();
            dimensionTypeBeanOfOrg.setName(itemBean.getMpName());
            dimensionTypeBeanOfOrg.setDimensionId(itemBean.getmPDimension());
            dimensionTypeBeans.add(dimensionTypeBeanOfOrg);
        }
        tvDimensionType.setText(dimensionTypeBeans.get(0).getName());
        dialogForDimension = new DialogForDimension(mContext,dimensionTypeBeans,this);
        dialogForChartType.setCanceledOnTouchOutside(true);
    }

    private void setChartData(IndexChartBean chartBean) {
        chartType = chartBean.getChart_type() ;
        tvNote.setText(resources.getString(R.string.note)
                + bean.getChartBean().getIndex_description());
        tvTarget.setVisibility(TextUtils.isEmpty(chartBean.getThresholdTarget()) ? View.GONE : View.VISIBLE);
        tvGuaranteed.setVisibility(TextUtils.isEmpty(chartBean.getThresholdMinimum()) ? View.GONE : View.VISIBLE);
        tvChallenge.setVisibility(TextUtils.isEmpty(chartBean.getThresholdChallenge()) ? View.GONE : View.VISIBLE);
        if(!TextUtils.isEmpty(chartBean.getThresholdTarget())){
            tvTarget.setText((TextUtils.isEmpty(chartBean.getThresholdTargetName())
                    ?resources.getString(R.string.the_target) :chartBean.getThresholdTargetName())
                    + ":" + chartBean.getThresholdTarget() + "(" + (TextUtils.isEmpty(chartBean.getThresholdUnit())
                    ? chartBean.getChart_unit() : chartBean.getThresholdUnit()) + ")");
        }
        if(!TextUtils.isEmpty(chartBean.getThresholdMinimum())){
            tvGuaranteed.setText((TextUtils.isEmpty(chartBean.getThresholdMinimumName())
                    ?resources.getString(R.string.guaranteed) :chartBean.getThresholdMinimumName())
                    + ":" + chartBean.getThresholdMinimum() + "(" + (TextUtils.isEmpty(chartBean.getThresholdUnit())
                    ? chartBean.getChart_unit() : chartBean.getThresholdUnit()) + ")");
        }
        if(!TextUtils.isEmpty(chartBean.getThresholdChallenge())){
            tvChallenge.setText((TextUtils.isEmpty(chartBean.getThresholdChallengeName())
                    ?resources.getString(R.string.challenge) :chartBean.getThresholdChallengeName())
                    + ":" + chartBean.getThresholdChallenge() + "(" + (TextUtils.isEmpty(chartBean.getThresholdUnit())
                    ? chartBean.getChart_unit() : chartBean.getThresholdUnit()) + ")");
        }

    }

    private void createChartTypePopData(DimensionPositionBean.IndexPositionBean chartBean) {
        ChartTypeBean showChartTypeBean = new ChartTypeBean() ;
        showChartTypeBean.setEnviroment(chartBean.getPage_chart_type());
        String[] chartTypes = chartBean.getChart_type().split(",");
        for (String type : chartTypes){
            ChartTypeBean chartTypeBean = new ChartTypeBean();
            chartTypeBean.setEnviroment(type);
            chartTypeBeans.add(chartTypeBean);
        }
        refreshChartValue(showChartTypeBean);
        tvChartType.setText(chartTypeBeans.get(0).getChartName());
        dialogForChartType = new DialogForChartType(mContext,chartTypeBeans,this);
        dialogForChartType.setCanceledOnTouchOutside(true);
    }

    /**
     * 刷新图表数据
     * @param showChartTypeBean
     */
    private void refreshChartValue(ChartTypeBean showChartTypeBean) {
        switch (showChartTypeBean.getChartType()){
            case "text" :
            case "long_text" :
                statusChart.showContent();
                TextView tvName = statusChart.findViewById(R.id.tv_name);
                if(TextUtils.isEmpty(bean.getChartBean().getChart_top_title())){
                    tvName.setText(bean.getChartBean().getName());
                }else {
                    tvName.setText(bean.getChartBean().getChart_top_title());
                }

                ((TextView)statusChart.findViewById(R.id.tv_defaultValue))
                        .setText(bean.getChartBean().getIndex_data());
                ((TextView)statusChart.findViewById(R.id.tv_unit))
                        .setText(bean.getChartBean().getChart_unit());
                break;
            case "dashboard" :
                statusChart.showExtendContent();
                TextView tvDashboardName = statusChart.findViewById(R.id.tv_name);
                if(TextUtils.isEmpty(bean.getChartBean().getChart_top_title())){
                    tvDashboardName.setText(bean.getChartBean().getName());
                }else {
                    tvDashboardName.setText(bean.getChartBean().getChart_top_title());
                }

                ((DashboardChart)statusChart.findViewById(R.id.dashBoardChart))
                        .drawData(bean.getChartBean());
                ((TextView)statusChart.findViewById(R.id.tv_unit))
                        .setText(bean.getChartBean().getChart_unit());
                break;
            case "bullet_map" :
                statusChart.showLoadingContent();
                TextView tvBulletName = statusChart.findViewById(R.id.tv_name);
                if(TextUtils.isEmpty(bean.getChartBean().getChart_top_title())){
                    tvBulletName.setText(bean.getChartBean().getName());
                }else {
                    tvBulletName.setText(bean.getChartBean().getChart_top_title());
                }
                ((TextView)statusChart.findViewById(R.id.tv_unit))
                        .setText(bean.getChartBean().getChart_unit());
                ((BulletChart)statusChart.findViewById(R.id.bullet_chart)).setValues(bean.getChartBean());
                ((BulletChart)statusChart.findViewById(R.id.bullet_chart)).setmUnit(bean.getChartBean().getChart_unit());
                break;
            case "line_chart" :
                statusChart.showErrorContent();
                TextView tvLineName = statusChart.findViewById(R.id.tv_name);
                if(TextUtils.isEmpty(bean.getChartBean().getChart_top_title())){
                    tvLineName.setText(bean.getChartBean().getName());
                }else {
                    tvLineName.setText(bean.getChartBean().getChart_top_title());
                }
                ((TextView)statusChart.findViewById(R.id.tv_unit))
                        .setText(bean.getChartBean().getChart_unit());

                LinChartBaseBean linChartBaseBean = new Gson()
                        .fromJson(bean.getChartBean().getOption(),LinChartBaseBean.class);

                AASeriesElement[] aaSeriesElements = new AASeriesElement[linChartBaseBean.getLegend().getData().size()];
                for(int i = 0 ; i < linChartBaseBean.getLegend().getData().size() ; i ++){
                    List<Object> datas = new ArrayList<>() ;
                    for (String arg : linChartBaseBean.getSeries().get(i).getData()){
                        if(TextUtils.isEmpty(arg)){
                            datas.add("");
                        }else{
                            datas.add(Double.valueOf(arg));
                        }
                    }
                    AASeriesElement element = new AASeriesElement()
                            .name(linChartBaseBean.getLegend().getData().get(i))
                            .data(datas.toArray());
                    aaSeriesElements[i] = element ;
                }

                String[] array =new String[linChartBaseBean.getxAxis().get(0).getData().size()];

                AAChartModel aaChartModel = new AAChartModel();

                aaChartModel.chartType(AAChartType.Line)
                        .inverted(false)
                        .title("")
                        .yAxisTitle("")
                        .legendEnabled(true)
                        .markerRadius(3f)
                        .yAxisMin(0f)
                        .categories(linChartBaseBean.getxAxis().get(0).getData().toArray(array))
                        .colorsTheme(new String[]{"#fe117c","#ffc069","#06caf4","#7dffc0"})
                        .series(aaSeriesElements);

                ((AAChartView)statusChart.findViewById(R.id.barChart)).aa_drawChartWithChartModel(aaChartModel);
                break;

            case "bar_chart" :
                statusChart.showErrorContent();
                TextView tvBareName = statusChart.findViewById(R.id.tv_name);
                if(TextUtils.isEmpty(bean.getChartBean().getChart_top_title())){
                    tvBareName.setText(bean.getChartBean().getName());
                }else {
                    tvBareName.setText(bean.getChartBean().getChart_top_title());
                }
                ((TextView)statusChart.findViewById(R.id.tv_unit))
                        .setText(bean.getChartBean().getChart_unit());

                LinChartBaseBean barChartBaseBean = new Gson()
                        .fromJson(bean.getChartBean().getOption(),LinChartBaseBean.class);

                String[] category ;
                if(barChartBaseBean != null && barChartBaseBean.getxAxis()!= null && barChartBaseBean.getSeries().size() > 0){
                    category = new String[barChartBaseBean.getSeries().get(0).getData().size()];
                    List<String> xAxisName = new ArrayList<>() ;
                    for (String data : barChartBaseBean.getxAxis().get(0).getData()){
                        xAxisName.add(data);
                    }
                    category = xAxisName.toArray(category) ;
                }else{
                    return;
                }
                AALabels aaLabels = new AALabels()
                        .enabled(true)
                        .style(new AAStyle()
                                .color(AAColor.LightGray));
                AAXAxis aaXAxis = new AAXAxis()
                        .visible(true)
                        .labels(aaLabels)
                        .min(0f)
                        .categories(category);

                AATooltip aaTooltip = new AATooltip()
                        .enabled(true)
                        .shared(true);

                AAPlotOptions aaPlotOptions = new AAPlotOptions()
                        .series(new AASeries()
                                .animation(new AAAnimation()
                                        .easing(AAChartAnimationType.EaseTo)
                                        .duration(1000)))
                        .bar(new AABar()
                                .grouping(false)
                                .pointPadding(0f)
                                .pointPlacement(0f)
                        );

                /**
                 * 标注
                 */
                AALegend aaLegend = new AALegend()
                        .enabled(true)
                        .itemStyle(new AAItemStyle()
                                .color(AAColor.LightGray))
                        .layout(AAChartLayoutType.Horizontal)
                        .align(AAChartAlignType.Center)
                        .x(0f)
                        .verticalAlign(AAChartVerticalAlignType.Bottom)
                        .y(0f);

                Object[] aaSeriesElement ;
                AAYAxis[] aayAxes = new AAYAxis[barChartBaseBean.getyAxis().size()];
                for (int i = 0 ; i < barChartBaseBean.getyAxis().size() ; i++){
                    AAYAxis aayAxis = new AAYAxis()
                            .title(new AATitle().text(barChartBaseBean.getyAxis().get(i).getName())).opposite(i!= 0);
                    aayAxes[i] = aayAxis ;
                }
                if(barChartBaseBean != null && barChartBaseBean.getSeries()!= null && barChartBaseBean.getSeries().size() > 0) {
                    aaSeriesElement = new Object[barChartBaseBean.getSeries().size()];
                    for (int i = 0; i < barChartBaseBean.getSeries().size(); i++) {
                        Object[] dataObj;
                        if (barChartBaseBean.getSeries().get(i).getData() != null
                                && barChartBaseBean.getSeries().get(i).getData().size() > 0) {
                            dataObj = new Object[barChartBaseBean.getSeries().get(i).getData().size()];
                            for (int j = 0; j < barChartBaseBean.getSeries().get(i).getData().size(); j++) {
                                if (TextUtils.isEmpty(barChartBaseBean.getSeries().get(i).getData().get(j))) {
                                    dataObj[j] = barChartBaseBean.getSeries().get(i).getData().get(j);
                                } else {
                                    dataObj[j] = Float.valueOf(barChartBaseBean.getSeries().get(i).getData().get(j));
                                }
                            }
                            if (i != 1) {
                                aaSeriesElement[i] = new AASeriesElement().name(barChartBaseBean.getSeries().get(i).getName()).type(AAChartType.Column)
                                        .borderWidth(0f).yAxis(0).color(bean.getChartBean()
                                                .getIndex_default_color()).data(dataObj);
                            } else {
                                aaSeriesElement[i] = new AASeriesElement().name(barChartBaseBean.getSeries().get(i).getName()).type(AAChartType.Column)
                                        .borderWidth(0f).yAxis(0).data(dataObj);
                            }

                        }
                    }
                    AAOptions aaOptions = new AAOptions()
                            .title(new AATitle().text(""))
                            .touchEventEnabled(false)
                            .xAxis(aaXAxis)
                            .yAxisArray(aayAxes)
                            .tooltip(aaTooltip)
                            .plotOptions(aaPlotOptions)
                            .legend(aaLegend)
                            .series(aaSeriesElement);
                    ((AAChartView)statusChart.findViewById(R.id.barChart)).aa_drawChartWithChartOptions(aaOptions);
                }


                break;

            case "pie_chart" :
                statusChart.showErrorContent();
                TextView tvPieName = statusChart.findViewById(R.id.tv_name);
                if(TextUtils.isEmpty(bean.getChartBean().getChart_top_title())){
                    tvPieName.setText(bean.getChartBean().getName());
                }else {
                    tvPieName.setText(bean.getChartBean().getChart_top_title());
                }
                ((TextView)statusChart.findViewById(R.id.tv_unit))
                        .setText(bean.getChartBean().getChart_unit());

                PieChartBaseBean baseBean = new Gson()
                        .fromJson(bean.getChartBean().getOption(),PieChartBaseBean.class);
                if(!(baseBean != null && baseBean.getSeries() != null && baseBean.getSeries().size() > 0)){
                    return;
                }
                Object[][] datas = new Object[baseBean.getSeries().get(0).getData().size()][2];
                for (int i = 0 ; i < baseBean.getSeries().get(0).getData().size() ; i ++){
                    Object[] items = new Object[]{baseBean.getSeries().get(0).getData().get(i).getName()
                            ,TextUtils.isEmpty(baseBean.getSeries().get(0).getData().get(i).getValue())? ""
                            : Double.valueOf(baseBean.getSeries().get(0).getData().get(i).getValue())};
                    datas[i] = items ;
                }
                AAChartModel aaChartModelOfPie = new AAChartModel()
                        .chartType(AAChartType.Pie)
                        .backgroundColor("#ffffff")
                        .title("")
                        .subtitle("")
                        .legendEnabled(false)
                        .dataLabelsEnabled(false)
                        .colorsTheme(baseBean.getColorsBean())
                        .yAxisTitle("")
                        .series(new AAPie[] {
                                        new AAPie()
                                                .name("")
                                                .innerSize("20%")
                                                .size(120f)
                                                .dataLabels(new AADataLabels()
                                                        .enabled(true)
                                                        .useHTML(true)
                                                        .distance(5f)
                                                        .format("<b>{point.name}</b>"))
                                                .data(datas)
                                        ,
                                }
                        );
                ((AAChartView)statusChart.findViewById(R.id.barChart)).aa_drawChartWithChartModel(aaChartModelOfPie);
                break;
        }
    }

    @OnClick({R.id.img_left,R.id.tv_chartType,R.id.tv_title,R.id.tv_dimensionType})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                    EventBus.getDefault().post(new RefreshEvent());
                break;

            case R.id.tv_chartType :
                    dialogForChartType.show();
                break;

            case R.id.tv_dimensionType :
                dialogForDimension.show();
                break;

            case R.id.tv_title :
                if(pvCustomTime.isShowing()){
                    pvCustomTime.dismiss();
                }else{
                    pvCustomTime.show();
                }
                break;
        }
    }

    @Override
    public void onTypeClick(ChartTypeBean chartTypeBean) {
        dialogForChartType.dismiss();
        tvChartType.setText(chartTypeBean.getChartName());
        chartType = chartTypeBean.getChartType() ;
        getEChart();
    }

    /**
     * 初始化时间选择器
     * @param hasYear
     * @param hasMonth
     * @param hasDay
     * @param timeFormat
     */
    private void initCustomPickView(boolean hasYear, boolean hasMonth, boolean hasDay, String timeFormat) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date(TimeUtils.parse(PreferencesHelper.get(Constants.USER_DEFAULT_TIME
                ,selectedDate.getTime().toString())).getTime()));
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2000,1,1);
        endDate.set(selectedDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)
                , endDate.get(Calendar.DATE));
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvTitle.setText(TimeUtils.date2Str(date,timeFormat));
                getEChart();
            }
        })
                .setType(new boolean[]{hasYear, hasMonth, hasDay, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(view -> {
                            pvCustomTime.returnData();
                            pvCustomTime.dismiss();
                        });
                        ivCancel.setOnClickListener(view -> {
                            pvCustomTime.dismiss();
                        });
                    }
                })
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("")
                .setOutSideCancelable(false)
                .isCyclic(false)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .isDialog(true)
                .build();
    }

    /**
     * 查询图表数据
     */
    private void getEChart() {
        HashMap params = new HashMap() ;
        params.put("lang", LanguageUtils.isZh(mContext) ?"zh" : "en");
        params.put("timeValue",tvTitle.getText().toString().replaceAll("-",""));
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
        chartTypeBean.setIndexId(bean.getIndex_id());
        chartTypeBean.setDataType(chartType);
        chartTypeBean.setAnalysisDim(dimensionSelect);
        chartTypeBean.setDefaultAnalysis(dimensionSelect.equals("TIME") ? "" : dimensionSelect);
        beans.add(chartTypeBean);
        params.put("chartType",beans);
        getPresenter().getChartData(params);
    }

    @Override
    public void getChartSuccess(IndexChartBean bean) {
        this.bean.setChartBean(bean);
        ChartTypeBean showChartTypeBean = new ChartTypeBean() ;
        showChartTypeBean.setEnviroment(bean.getChart_type());
        refreshChartValue(showChartTypeBean);
    }

    @Override
    public void onDimensionClick(DimensionTypeBean chartTypeBean) {
        dialogForDimension.dismiss();
        dimensionSelect = chartTypeBean.getDimensionId();
        tvDimensionType.setText(chartTypeBean.getName());
        getEChart();
    }
}
