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
import com.datacvg.dimp.bean.DimensionTypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-09
 * @Description :
 */
public class SelectChartDimensionAdapter extends RecyclerView.Adapter<SelectChartDimensionAdapter.ViewHolder> {

    private Context mContext ;
    private List<DimensionTypeBean> dimensionTypeBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnTypeClickListener clickListener ;

    public SelectChartDimensionAdapter(Context mContext, List<DimensionTypeBean> dimensionTypeBeans
            ,OnTypeClickListener clickListener) {
        this.mContext = mContext;
        this.dimensionTypeBeans = dimensionTypeBeans;
        inflater = LayoutInflater.from(mContext) ;
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
        DimensionTypeBean chartTypeBean = dimensionTypeBeans.get(position);
        holder.tvTypeName.setText(chartTypeBean.getName());
        holder.itemView.setOnClickListener(view -> {
            clickListener.onDimensionClick(chartTypeBean);
        });
    }

    @Override
    public int getItemCount() {
        return dimensionTypeBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_typeName)
        TextView tvTypeName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnTypeClickListener {
        void onDimensionClick(DimensionTypeBean chartTypeBean);
    }
}
