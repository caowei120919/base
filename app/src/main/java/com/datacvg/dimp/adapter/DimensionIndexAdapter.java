package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ChartBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.chart.PieChartBean;
import com.datacvg.dimp.widget.BarChart;
import com.datacvg.dimp.widget.DashBoardView;
import com.datacvg.dimp.widget.LineChart;
import com.datacvg.dimp.widget.PieChart;
import com.google.gson.internal.LinkedTreeMap;

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
    private List<DimensionPositionBean> chartBeans = new ArrayList<>();
    private IndexClickListener listener ;

    public DimensionIndexAdapter(Context mContext, List<DimensionPositionBean> chartBeans
            ,IndexClickListener listener) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.chartBeans = chartBeans;
        this.listener = listener ;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null ;
        switch (viewType){
            case TEXT_CHART :
                view = inflater.inflate(R.layout.item_chart_text,parent,false);
                return new TextHolder(view);

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
                view = inflater.inflate(R.layout.item_chart_none,parent,false);
                return new NoneHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chartBeans.size() == 0){
            return 0;
        }
        switch (chartBeans.get(position).getChart_type()){
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
        DimensionPositionBean dimensionPositionBean = chartBeans.get(position);
        List<Double> chartBeans = new ArrayList<>();
        List<String> chartXTitles = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        if (dimensionPositionBean.getChartBean() == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            listener.OnItemClick(dimensionPositionBean);
        });
        holder.tvUnit.setText(dimensionPositionBean.getChartBean().getChart_unit());
        holder.tvName.setText(LanguageUtils.isZh(mContext)
                ? dimensionPositionBean.getChartBean().getIndex_clname()
                : dimensionPositionBean.getChartBean().getIndex_flname());
        holder.tvDefaultValue.setText(dimensionPositionBean.getChartBean().getIndex_data() + "");
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean
                .getChartBean().getIndex_default_color()));
        List<Object> datas = dimensionPositionBean.getChartBean().getOption().getSeries().get(0).getData();
        for (int i = 0 ; i < datas.size() ; i++){
            chartBeans.add(Double.valueOf(datas.get(i) + ""));
            colors.add(Color.parseColor(dimensionPositionBean.getChartBean().getIndex_default_color()));
        }
        chartXTitles.addAll(dimensionPositionBean.getChartBean().getOption().getXAxis().get(0).getData());
        if(!TextUtils.isEmpty(dimensionPositionBean.getExistIndexthreshold())
                && dimensionPositionBean.getExistIndexthreshold().equals("true")){
            holder.imgIndexForReport.setVisibility(View.VISIBLE);
        }else{
            holder.imgIndexForReport.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(dimensionPositionBean.getExistDescription())
                && dimensionPositionBean.getExistDescription().equals("true")){
            switch (holder.imgIndexForReport.getVisibility()){
                case View.VISIBLE :
                    holder.imgDescribe.setVisibility(View.VISIBLE);
                    break;

                case View.GONE:
                    holder.imgIndexForReport.setVisibility(View.VISIBLE);
                    holder.imgIndexForReport.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_describe));
                    break;
            }
        }else{
            holder.imgDescribe.setVisibility(View.GONE);
        }
        holder.lineChart.setColumnInfo(chartXTitles,chartBeans,colors,6);
    }

    /**
     * 柱状图
     * @param holder
     * @param position
     */
    private void onBindViewBarHolder(BarHolder holder, int position) {
        DimensionPositionBean dimensionPositionBean = chartBeans.get(position);
        List<Double> chartBeans = new ArrayList<>();
        List<String> chartXTitles = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        if (dimensionPositionBean.getChartBean() == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            listener.OnItemClick(dimensionPositionBean);
        });
        holder.tvUnit.setText(dimensionPositionBean.getChartBean().getChart_unit());
        holder.tvName.setText(LanguageUtils.isZh(mContext)
                ? dimensionPositionBean.getChartBean().getIndex_clname()
                : dimensionPositionBean.getChartBean().getIndex_flname());
        holder.tvDefaultValue.setText(dimensionPositionBean.getChartBean().getIndex_data() + "");
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean
                .getChartBean().getIndex_default_color()));
        List<Object> datas = dimensionPositionBean.getChartBean().getOption().getSeries().get(0).getData();
        for (int i = 0 ; i < datas.size() ; i++){
            chartBeans.add(Double.valueOf(datas.get(i) + ""));
            colors.add(Color.parseColor(dimensionPositionBean.getChartBean().getIndex_default_color()));
        }
        chartXTitles.addAll(dimensionPositionBean.getChartBean().getOption().getXAxis().get(0).getData());
        if(!TextUtils.isEmpty(dimensionPositionBean.getExistIndexthreshold())
                && dimensionPositionBean.getExistIndexthreshold().equals("true")){
            holder.imgIndexForReport.setVisibility(View.VISIBLE);
        }else{
            holder.imgIndexForReport.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(dimensionPositionBean.getExistDescription())
                && dimensionPositionBean.getExistDescription().equals("true")){
            switch (holder.imgIndexForReport.getVisibility()){
                case View.VISIBLE :
                    holder.imgDescribe.setVisibility(View.VISIBLE);
                    break;

                case View.GONE:
                    holder.imgIndexForReport.setVisibility(View.VISIBLE);
                    holder.imgIndexForReport.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_describe));
                    break;
            }
        }else{
            holder.imgDescribe.setVisibility(View.GONE);
        }
        holder.barChart.setColumnInfo(chartXTitles,chartBeans,colors,6);
    }

    /**
     * 饼图
     * @param holder
     * @param position
     */
    private void onBindViewPieHolder(PieHolder holder, int position) {
        DimensionPositionBean dimensionPositionBean = chartBeans.get(position);
        List<PieChartBean> pieChartBeans = new ArrayList<>();
        if (dimensionPositionBean.getChartBean() == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            listener.OnItemClick(dimensionPositionBean);
        });
        holder.tvUnit.setText(dimensionPositionBean.getChartBean().getChart_unit());
        holder.tvName.setText(LanguageUtils.isZh(mContext)
                ? dimensionPositionBean.getChartBean().getIndex_clname()
                : dimensionPositionBean.getChartBean().getIndex_flname());
        holder.tvDefaultValue.setText(dimensionPositionBean.getChartBean().getIndex_data() + "");
        holder.tvDefaultValue.setTextColor(Color.parseColor(dimensionPositionBean
                .getChartBean().getIndex_default_color()));
        List<Object> datas = dimensionPositionBean.getChartBean().getOption().getSeries().get(0).getData();
        for (int i = 0 ; i < datas.size() ; i++){
            PieChartBean pieChartBean = new PieChartBean();
            pieChartBean.setName((String) ((LinkedTreeMap)datas.get(i)).get("name"));
            pieChartBean.setValue(Float.valueOf((String) ((LinkedTreeMap)datas.get(i)).get("value")));
            pieChartBeans.add(pieChartBean);
        }
        if(!TextUtils.isEmpty(dimensionPositionBean.getExistIndexthreshold())
                && dimensionPositionBean.getExistIndexthreshold().equals("true")){
            holder.imgIndexForReport.setVisibility(View.VISIBLE);
        }else{
            holder.imgIndexForReport.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(dimensionPositionBean.getExistDescription())
                && dimensionPositionBean.getExistDescription().equals("true")){
            switch (holder.imgIndexForReport.getVisibility()){
                case View.VISIBLE :
                    holder.imgDescribe.setVisibility(View.VISIBLE);
                    break;

                case View.GONE:
                    holder.imgIndexForReport.setVisibility(View.VISIBLE);
                    holder.imgIndexForReport.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_describe));
                    break;
            }
        }else{
            holder.imgDescribe.setVisibility(View.GONE);
        }
        holder.pieChart.setDate(pieChartBeans);
    }

    /**
     * 仪表盘
     * @param holder
     * @param position
     */
    private void onBindViewDashBoardHolder(DashBoardHolder holder, int position) {
        DimensionPositionBean dimensionPositionBean = chartBeans.get(position);
        if (dimensionPositionBean.getChartBean() == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            listener.OnItemClick(dimensionPositionBean);
        });
        holder.tvUnit.setText(dimensionPositionBean.getChartBean().getChart_unit());
        holder.tvName.setText(LanguageUtils.isZh(mContext)
                ? dimensionPositionBean.getChartBean().getIndex_clname()
                : dimensionPositionBean.getChartBean().getIndex_flname());
        if(!TextUtils.isEmpty(dimensionPositionBean.getExistIndexthreshold())
                && dimensionPositionBean.getExistIndexthreshold().equals("true")){
            holder.imgIndexForReport.setVisibility(View.VISIBLE);
        }else{
            holder.imgIndexForReport.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(dimensionPositionBean.getExistDescription())
                && dimensionPositionBean.getExistDescription().equals("true")){
            switch (holder.imgIndexForReport.getVisibility()){
                case View.VISIBLE :
                    holder.imgDescribe.setVisibility(View.VISIBLE);
                    break;

                case View.GONE:
                    holder.imgIndexForReport.setVisibility(View.VISIBLE);
                    holder.imgIndexForReport.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_describe));
                    break;
            }
        }else{
            holder.imgDescribe.setVisibility(View.GONE);
        }
        holder.dashBoardChart.setChartValue(dimensionPositionBean.getChartBean());
    }

    /**
     * 子弹图
     * @param holder
     * @param position
     */
    private void onBindViewBulletHolder(BulletHolder holder, int position) {

    }

    /**
     * 长文本
     * @param holder
     * @param position
     */
    private void onBindViewLongHolder(LongTextHolder holder, int position) {
        DimensionPositionBean dimensionPositionBean = chartBeans.get(position) ;
        ChartBean bean = dimensionPositionBean.getChartBean();
        if(bean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            listener.OnItemClick(chartBeans.get(position));
        });
        holder.tvName.setText(LanguageUtils.isZh(mContext)
                ? bean.getIndex_clname() : bean.getIndex_flname());
        String bottomValue = TextUtils.isEmpty(bean.getChart_bottom_title())
                ? "" : bean.getChart_bottom_title()
                + ((TextUtils.isEmpty(bean.getBottom_value()))
                ? "" : bean.getBottom_value());
        holder.tvBottomValue.setText(bottomValue);
        holder.tvUnit.setText(bean.getChart_unit());
        holder.tvDefaultValue.setText(bean.getDefault_value());
        holder.tvDefaultValue.setTextColor(Color.parseColor(bean.getIndex_default_color()));
        if(!TextUtils.isEmpty(dimensionPositionBean.getExistIndexthreshold())
                && dimensionPositionBean.getExistIndexthreshold().equals("true")){
            holder.imgIndexForReport.setVisibility(View.VISIBLE);
        }else{
            holder.imgIndexForReport.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(dimensionPositionBean.getExistDescription())
                && dimensionPositionBean.getExistDescription().equals("true")){
            switch (holder.imgIndexForReport.getVisibility()){
                case View.VISIBLE :
                    holder.imgDescribe.setVisibility(View.VISIBLE);
                    break;

                case View.GONE:
                    holder.imgIndexForReport.setVisibility(View.VISIBLE);
                    holder.imgIndexForReport.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_describe));
                    break;
            }
        }else{
            holder.imgDescribe.setVisibility(View.GONE);
        }
    }

    /**
     * 短文本
     * @param holder
     * @param position
     */
    private void onBindViewTextHolder(TextHolder holder, int position) {
        DimensionPositionBean dimensionPositionBean = chartBeans.get(position) ;
        ChartBean bean = dimensionPositionBean.getChartBean();
        if(bean == null){
            return;
        }
        holder.itemView.setOnClickListener(view -> {
            PLog.e("点击");
            listener.OnItemClick(chartBeans.get(position));
        });
        holder.tvName.setText(LanguageUtils.isZh(mContext)
                ? bean.getIndex_clname() : bean.getIndex_flname());
        String bottomValue = TextUtils.isEmpty(bean.getChart_bottom_title())
                ? "" : bean.getChart_bottom_title()
                + ((TextUtils.isEmpty(bean.getBottom_value()))
                ? "" : bean.getBottom_value());
        holder.tvBottomValue.setText(bottomValue);
        holder.tvUnit.setText(bean.getChart_unit());
        holder.tvDefaultValue.setText(bean.getDefault_value());
        holder.tvDefaultValue.setTextColor(Color.parseColor(bean.getIndex_default_color()));
        if(!TextUtils.isEmpty(dimensionPositionBean.getExistIndexthreshold())
                && dimensionPositionBean.getExistIndexthreshold().equals("true")){
            holder.imgIndexForReport.setVisibility(View.VISIBLE);
        }else{
            holder.imgIndexForReport.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(dimensionPositionBean.getExistDescription())
                && dimensionPositionBean.getExistDescription().equals("true")){
            switch (holder.imgIndexForReport.getVisibility()){
                case View.VISIBLE :
                       holder.imgDescribe.setVisibility(View.VISIBLE);
                    break;

                case View.GONE:
                        holder.imgIndexForReport.setVisibility(View.VISIBLE);
                        holder.imgIndexForReport.setImageBitmap(BitmapFactory
                                .decodeResource(mContext.getResources(),R.mipmap.icon_describe));
                    break;
            }
        }else{
            holder.imgDescribe.setVisibility(View.GONE);
        }
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
        LineChart lineChart ;
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
        BarChart barChart ;
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
        PieChart pieChart ;
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
        public BulletHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class DashBoardHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.dashBoardChart)
        DashBoardView dashBoardChart ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_indexForReport)
        ImageView imgIndexForReport ;
        @BindView(R.id.img_describe)
        ImageView imgDescribe ;
        @BindView(R.id.tv_unit)
        TextView tvUnit ;

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
        void OnTitleClick(DimensionPositionBean bean);
        void OnItemClick(DimensionPositionBean bean);
    }
}