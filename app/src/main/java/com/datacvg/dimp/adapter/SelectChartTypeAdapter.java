package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.ChartTypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-07
 * @Description :
 */
public class SelectChartTypeAdapter extends RecyclerView.Adapter<SelectChartTypeAdapter.ViewHolder> {
    private Context mContext ;
    private List<ChartTypeBean> chartTypeBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private OnTypeClickListener clickListener ;

    public SelectChartTypeAdapter(Context mContext, List<ChartTypeBean> chartTypeBeans
            ,OnTypeClickListener clickListener) {
        this.mContext = mContext;
        this.chartTypeBeans = chartTypeBeans;
        inflater = LayoutInflater.from(mContext);
        this.clickListener = clickListener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_select_chart_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChartTypeBean chartTypeBean = chartTypeBeans.get(position);
        holder.tvTypeName.setText(chartTypeBean.getChartName());
        holder.itemView.setOnClickListener(view -> {
            clickListener.onTypeClick(chartTypeBean);
        });
    }

    @Override
    public int getItemCount() {
        return chartTypeBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_typeName)
        TextView tvTypeName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnTypeClickListener{
        void onTypeClick(ChartTypeBean chartTypeBean);
    }
}
