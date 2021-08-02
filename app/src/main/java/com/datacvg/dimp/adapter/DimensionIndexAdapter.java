package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.LinChartBaseBean;
import com.datacvg.dimp.bean.PieChartBaseBean;
import com.datacvg.dimp.widget.BulletChart;
import com.datacvg.dimp.widget.DashboardChart;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 数字神经指标列表适配器
 */
public class DimensionIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TEXT_CHART = 1 ;
    private final int LONG_TEXT_CHART = 2 ;
    private final int LINE_CHART = 3 ;
    private final int BAR_CHART = 4 ;
    private final int PIE_CHART = 5 ;
    private final int BULLET_CHART = 6 ;
    private final int DASHBOARD_CHART = 7 ;

    private Context mContext ;
    private LayoutInflater inflater ;
    private List<DimensionPositionBean.IndexPositionBean> chartBeans = new ArrayList<>();
    private boolean mShake = false ;
    private IndexClickListener indexClickListener ;


    public DimensionIndexAdapter(Context mContext
            , List<DimensionPositionBean.IndexPositionBean> chartBeans,IndexClickListener indexClickListener) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.chartBeans = chartBeans;
        this.indexClickListener = indexClickListener ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null ;
        switch (viewType){
            case LONG_TEXT_CHART :
                view = inflater.inflate(R.layout.item_chart_long_text,parent,false);
                return new LongTextHolder(view);
            case LINE_CHART :
                view = inflater.inflate(R.layout.item_chart_line,parent,false);
                return new LineHolder(view);
            case BAR_CHART :
                view = inflater.inflate(R.layout.item_chart_bar,parent,false);
                return new BarHolder(view);
            case PIE_CHART :
                view = inflater.inflate(R.layout.item_chart_pie,parent,false);
                return new PieHolder(view);
            case BULLET_CHART :
                view = inflater.inflate(R.layout.item_chart_bullet,parent,false);
                return new BulletHolder(view);
            case DASHBOARD_CHART :
                view = inflater.inflate(R.layout.item_chart_dashboard,parent,false);
                return new DashBoardHolder(view);
            default:
                view = inflater.inflate(R.layout.item_chart_text,parent,false);
                return new TextHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chartBeans.size() == 0){
            return 0;
        }
        switch (chartBeans.get(position).getPage_chart_type()){
            case "long_text" :
                return LONG_TEXT_CHART ;

            case "line_chart" :
                return LINE_CHART ;

            case "bar_chart" :
                return BAR_CHART ;

            case "pie_chart" :
                return PIE_CHART ;

            case "dashboard" :
                return DASHBOARD_CHART ;

            case "bullet_map" :
                return BULLET_CHART ;

            default:
                return TEXT_CHART ;
        }
    }

    public void setHolderShake(boolean isShake){
        mShake = isShake ;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(mShake){
            holder.itemView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.shake));
            holder.itemView.getAnimation().start();
        }else{
            if(holder.itemView.getAnimation() != null && holder.itemView.getAnimation().hasStarted()){
                holder.itemView.getAnimation().cancel();
            }
        }
        if(holder instanceof TextHolder){
            onBindViewTextHolder((TextHolder) holder,position);
        }else if(holder instanceof LongTextHolder){
            onBindViewLongHolder((LongTextHolder) holder,position);
        }else if(holder instanceof LineHolder){
            onBindViewLineHolder((LineHolder) holder,position);
        }else if(holder instanceof BarHolder){
            onBindViewBarHolder((BarHolder) holder,position);
        }else if(holder instanceof PieHolder){
            onBindViewPieHolder((PieHolder) holder,position);
        }else if(holder instanceof DashBoardHolder){
            onBindViewDashBoardHolder((DashBoardHolder) holder,position);
        }else if(holder instanceof BulletHolder){
            onBindViewBulletHolder((BulletHolder) holder,position);
        }
    }

    /**
     * 折线图
     * @param holder
     * @param position
     */
    private void onBindViewLineHolder(LineHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean();
        if(dimensionPositionBean == null){
            return;
        }
        LinChartBaseBean linChartBaseBean = new Gson()
                .fromJson(dimensionPositionBean.getOption(),LinChartBaseBean.class);
        String[] category ;
        if(linChartBaseBean != null && linChartBaseBean.getxAxis()!= null && linChartBaseBean.getSeries().size() > 0){
            List<String> xAxisName = new ArrayList<>() ;
            for (String data : linChartBaseBean.getxAxis().get(0).getData()){
                xAxisName.add(data);
            }
            category = new String[xAxisName.size()];
            category = xAxisName.toArray(category) ;

            AALabels aaLabels = new AALabels()
                    .enabled(true)
                    .style(new AAStyle()
                            .color(AAColor.LightGray));
            AAXAxis aaXAxis = new AAXAxis()
                    .visible(true)
                    .labels(aaLabels)
                    .categories(category);

            AATooltip aaTooltip = new AATooltip()
                    .enabled(false)
                    .shared(false);

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
                    .enabled(false)
                    .itemStyle(new AAItemStyle()
                            .color(AAColor.LightGray))
                    .layout(AAChartLayoutType.Horizontal)
                    .align(AAChartAlignType.Center)
                    .x(0f)
                    .verticalAlign(AAChartVerticalAlignType.Bottom)
                    .y(0f);

            Object[] aaSeriesElement ;
            AAYAxis[] aayAxes = new AAYAxis[linChartBaseBean.getyAxis().size()];
            for (int i = 0 ; i < linChartBaseBean.getyAxis().size() ; i++){
                AAYAxis aayAxis = new AAYAxis()
                        .title(new AATitle().text(linChartBaseBean.getyAxis().get(i).getName())).opposite(i!= 0);
                aayAxes[i] = aayAxis ;
            }

            aaSeriesElement = new Object[linChartBaseBean.getSeries().size()];
            for (int i = 0; i < linChartBaseBean.getSeries().size(); i++) {
                Object[] dataObj;
                if (linChartBaseBean.getSeries().get(i).getData() != null
                        && linChartBaseBean.getSeries().get(i).getData().size() > 0) {
                    dataObj = new Object[linChartBaseBean.getSeries().get(i).getData().size()];
                    for (int j = 0; j < linChartBaseBean.getSeries().get(i).getData().size(); j++) {
                        if (TextUtils.isEmpty(linChartBaseBean.getSeries().get(i).getData().get(j))) {
                            dataObj[j] = linChartBaseBean.getSeries().get(i).getData().get(j);
                        } else {
                            dataObj[j] = Float.valueOf(linChartBaseBean.getSeries().get(i).getData().get(j));
                        }
                    }
                    if (i != 1) {
                        aaSeriesElement[i] = new AASeriesElement().name(linChartBaseBean.getSeries().get(i).getName()).type(AAChartType.Line)
                                .borderWidth(0f).yAxis(0).color(dimensionPositionBean
                                        .getIndex_default_color()).data(dataObj).allowPointSelect(false);
                    } else {
                        aaSeriesElement[i] = new AASeriesElement().name(linChartBaseBean.getSeries().get(i).getName()).type(AAChartType.Line)
                                .borderWidth(0f).yAxis(aayAxes.length > 1 ? 1 : 0).data(dataObj).allowPointSelect(false);
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
            holder.lineChart.aa_drawChartWithChartOptions(aaOptions);
        }else{
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("点击");
            indexClickListener.OnItemClick(chartBeans.get(position));
        });
        holder.tvUnit.setText(dimensionPositionBean.getChart_unit());
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("标题被点击");
            indexClickListener.OnTitleClick(chartBeans.get(position));
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        holder.tvDefaultValue.setText(dimensionPositionBean.getIndex_data() + "");
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean.getIndex_default_color()));
    }

    /**
     * 柱状图
     * @param holder
     * @param position
     */
    private void onBindViewBarHolder(BarHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean();
        if (dimensionPositionBean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("点击");
            indexClickListener.OnItemClick(chartBeans.get(position));
        });
        holder.tvUnit.setText(dimensionPositionBean.getChart_unit());
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("标题被点击");
            indexClickListener.OnTitleClick(chartBeans.get(position));
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        holder.tvDefaultValue.setText(dimensionPositionBean.getIndex_data() + "");
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean
                .getIndex_default_color()));


        LinChartBaseBean linChartBaseBean = new Gson()
                .fromJson(dimensionPositionBean.getOption(),LinChartBaseBean.class);
        String[] category ;
        if(linChartBaseBean != null && linChartBaseBean.getxAxis()!= null && linChartBaseBean.getSeries().size() > 0){
            List<String> xAxisName = new ArrayList<>() ;
            for (String data : linChartBaseBean.getxAxis().get(0).getData()){
                xAxisName.add(data);
            }
            category = new String[xAxisName.size()];
            category = xAxisName.toArray(category) ;

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
                    .enabled(false)
                    .shared(false);

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
                    .enabled(false)
                    .itemStyle(new AAItemStyle()
                            .color(AAColor.LightGray))
                    .layout(AAChartLayoutType.Horizontal)
                    .align(AAChartAlignType.Center)
                    .x(0f)
                    .verticalAlign(AAChartVerticalAlignType.Bottom)
                    .y(0f);

            Object[] aaSeriesElement ;
            AAYAxis[] aayAxes = new AAYAxis[linChartBaseBean.getyAxis().size()];
            for (int i = 0 ; i < linChartBaseBean.getyAxis().size() ; i++){
                AAYAxis aayAxis = new AAYAxis()
                        .title(new AATitle().text(linChartBaseBean.getyAxis().get(i).getName())).opposite(i!= 0);
                aayAxes[i] = aayAxis ;
            }

            aaSeriesElement = new Object[linChartBaseBean.getSeries().size()];
            for (int i = 0; i < linChartBaseBean.getSeries().size(); i++) {
                Object[] dataObj;
                if (linChartBaseBean.getSeries().get(i).getData() != null
                        && linChartBaseBean.getSeries().get(i).getData().size() > 0) {
                    dataObj = new Object[linChartBaseBean.getSeries().get(i).getData().size()];
                    for (int j = 0; j < linChartBaseBean.getSeries().get(i).getData().size(); j++) {
                        if (TextUtils.isEmpty(linChartBaseBean.getSeries().get(i).getData().get(j))) {
                            dataObj[j] = linChartBaseBean.getSeries().get(i).getData().get(j);
                        } else {
                            dataObj[j] = Float.valueOf(linChartBaseBean.getSeries().get(i).getData().get(j));
                        }
                    }
                    if (i != 1) {
                        aaSeriesElement[i] = new AASeriesElement().name(linChartBaseBean.getSeries().get(i).getName()).type(AAChartType.Column)
                                .borderWidth(0f).yAxis(0).color(dimensionPositionBean
                                        .getIndex_default_color()).data(dataObj).allowPointSelect(false);
                    } else {
                        aaSeriesElement[i] = new AASeriesElement().name(linChartBaseBean.getSeries().get(i).getName()).type(AAChartType.Column)
                                .borderWidth(0f).yAxis(aayAxes.length > 1 ? 1 : 0).data(dataObj).allowPointSelect(false);
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
            holder.barChart.aa_drawChartWithChartOptions(aaOptions);
            holder.barChart.setEnabled(false);
        }else{
            return;
        }

    }

    /**
     * 饼图
     * @param holder
     * @param position
     */
    private void onBindViewPieHolder(PieHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean();
        if (dimensionPositionBean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("点击");
            indexClickListener.OnItemClick(chartBeans.get(position));
        });
        holder.tvUnit.setText(dimensionPositionBean.getChart_unit());
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("标题被点击");
            indexClickListener.OnTitleClick(chartBeans.get(position));
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        holder.tvDefaultValue.setText(dimensionPositionBean.getIndex_data() + "");
        holder.tvDefaultValue
                .setTextColor(Color.parseColor(dimensionPositionBean.getIndex_default_color()));

        PieChartBaseBean baseBean = new Gson()
                .fromJson(dimensionPositionBean.getOption(),PieChartBaseBean.class);
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
        AAChartModel aaChartModel = new AAChartModel()
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
                                        .size(150f)
                                        .allowPointSelect(false)
                                        .showInLegend(false)
                                        .dataLabels(new AADataLabels()
                                                .enabled(false)
                                                .allowOverlap(false)
                                                .useHTML(true)
                                                .distance(5f)
                                                .format("<b>{point.name}</b>"))
                                        .data(datas)
                                ,
                        }
                );
        holder.pieChart.aa_drawChartWithChartModel(aaChartModel);
        holder.pieChart.setEnabled(false);
    }

    /**
     * 仪表盘
     * @param holder
     * @param position
     */
    private void onBindViewDashBoardHolder(DashBoardHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean();
        if (dimensionPositionBean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            indexClickListener.OnItemClick(chartBeans.get(position));
            PLog.e("点击");
        });
        holder.tvUnit.setText(dimensionPositionBean.getChart_unit());
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("标题被点击");
            indexClickListener.OnTitleClick(chartBeans.get(position));
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        holder.dashBoardChart.drawData(dimensionPositionBean);
    }

    /**
     * 子弹图
     * @param holder
     * @param position
     */
    private void onBindViewBulletHolder(BulletHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean() ;
        if(dimensionPositionBean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            indexClickListener.OnItemClick(chartBeans.get(position));
            PLog.e("点击");
        });
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            PLog.e("标题被点击");
            indexClickListener.OnTitleClick(chartBeans.get(position));
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        holder.bulletChart.setValues(dimensionPositionBean);
        holder.bulletChart.setmUnit(dimensionPositionBean.getChart_unit());
    }

    /**
     * 长文本
     * @param holder
     * @param position
     */
    private void onBindViewLongHolder(LongTextHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean() ;
        if(dimensionPositionBean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            indexClickListener.OnItemClick(chartBeans.get(position));
            PLog.e("点击");
        });
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            indexClickListener.OnTitleClick(chartBeans.get(position));
            PLog.e("标题被点击");
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        String bottomValue = TextUtils.isEmpty(dimensionPositionBean.getChart_bottom_title())
                ? "" : dimensionPositionBean.getChart_bottom_title()
                + ((TextUtils.isEmpty(dimensionPositionBean.getBottom_value()))
                ? "" : dimensionPositionBean.getBottom_value());
        holder.tvBottomValue.setText(bottomValue);
        holder.tvUnit.setText(dimensionPositionBean.getChart_unit());
        holder.tvDefaultValue.setText(dimensionPositionBean.getIndex_data());
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean.getIndex_default_color()));
    }

    /**
     * 短文本
     * @param holder
     * @param position
     */
    private void onBindViewTextHolder(TextHolder holder, int position) {
        IndexChartBean dimensionPositionBean = chartBeans.get(position).getChartBean() ;
        if(dimensionPositionBean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            if(mShake){
                return;
            }
            indexClickListener.OnItemClick(chartBeans.get(position));
        });
        if(TextUtils.isEmpty(dimensionPositionBean.getChart_top_title())){
            holder.tvName.setText(dimensionPositionBean.getName());
        }else {
            holder.tvName.setText(dimensionPositionBean.getChart_top_title());
        }
        holder.tvName.setOnClickListener(view -> {
            if(mShake){
                return;
            }
            indexClickListener.OnTitleClick(chartBeans.get(position));
            PLog.e("标题被点击");
        });
        if (mShake){
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.dash_delete));
            holder.imgDescribe.setVisibility(View.GONE);
            holder.imgIndexForReport.setOnClickListener(view -> {
                PLog.e("删除指标");
                indexClickListener.OnIndexDeleteClick(chartBeans.get(position));
            });
        }else{
            holder.imgIndexForReport.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_index_report));
            holder.imgDescribe.setVisibility(View.VISIBLE);
        }
        String bottomValue = TextUtils.isEmpty(dimensionPositionBean.getChart_bottom_title())
                ? "" : dimensionPositionBean.getChart_bottom_title()
                + ((TextUtils.isEmpty(dimensionPositionBean.getBottom_value()))
                ? "" : dimensionPositionBean.getBottom_value());
        holder.tvBottomValue.setText(bottomValue);
        holder.tvUnit.setText(dimensionPositionBean.getChart_unit());
        holder.tvDefaultValue.setText(dimensionPositionBean.getIndex_data());
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean.getIndex_default_color()));
    }

    @Override
    public int getItemCount() {
        return chartBeans.size();
    }

    public class TextHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;
        @BindView(R.id.tv_defaultValue)
        TextView tvDefaultValue ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_bottomValue)
        TextView tvBottomValue ;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class LongTextHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;
        @BindView(R.id.tv_defaultValue)
        TextView tvDefaultValue ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_bottomValue)
        TextView tvBottomValue ;

        public LongTextHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public class LineHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.lineChart)
        AAChartView lineChart ;
        @BindView(R.id.tv_defaultValue)
        TextView tvDefaultValue ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;

        public LineHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class BarHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.barChart)
        AAChartView barChart ;
        @BindView(R.id.tv_defaultValue)
        TextView tvDefaultValue ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;

        public BarHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class PieHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.pieChart)
        AAChartView pieChart ;
        @BindView(R.id.tv_defaultValue)
        TextView tvDefaultValue ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;
        public PieHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class BulletHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bullet_chart)
        BulletChart bulletChart ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        public BulletHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class DashBoardHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.dashBoardChart)
        DashboardChart dashBoardChart ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;
        //
        public DashBoardHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class NoneHolder extends RecyclerView.ViewHolder{
        public NoneHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            ((GridLayoutManager)manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (getItemViewType(position)){
                        case TEXT_CHART :
                            return 1 ;

                        default:
                            return 2;
                    }
                }
            });
        }
    }

    public interface IndexClickListener{
        void OnTitleClick(DimensionPositionBean.IndexPositionBean bean);
        void OnItemClick(DimensionPositionBean.IndexPositionBean bean);
        void OnIndexDeleteClick(DimensionPositionBean.IndexPositionBean bean);
    }
}
