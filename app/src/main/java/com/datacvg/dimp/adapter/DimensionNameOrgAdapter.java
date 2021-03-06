package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.DimensionForTimeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-11
 * @Description :
 */
public class DimensionNameOrgAdapter extends RecyclerView.Adapter<DimensionNameOrgAdapter.ViewHolder> {
    private Context mContext ;
    private List<DimensionForTimeBean.DimensionRelationBean.DimensionNameBean> dimensionNameBeans
            = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private DimensionNameOrgClickListener clickListener ;

    public void setDimensionNameBeans(List<DimensionForTimeBean.DimensionRelationBean.DimensionNameBean> dimensionNameBeans) {
        this.dimensionNameBeans.clear();
        for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean bean : dimensionNameBeans){
            if (!bean.getSelected()){
                this.dimensionNameBeans.add(bean);
            }
        }
        notifyDataSetChanged();
    }

    public DimensionNameOrgAdapter(Context mContext
            , List<DimensionForTimeBean.DimensionRelationBean.DimensionNameBean> dimensionNameBeans
            , DimensionNameOrgClickListener clickListener) {
        this.mContext = mContext;
        this.dimensionNameBeans.addAll(dimensionNameBeans);
        this.clickListener = clickListener ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_dimension_name,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDimensionName.setText(dimensionNameBeans.get(position).getD_res_name());
        holder.itemView.setOnClickListener(v -> {
            clickListener.onDimensionOrgClick(dimensionNameBeans.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dimensionNameBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_dimensionName)
        TextView tvDimensionName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface DimensionNameOrgClickListener {
        void onDimensionOrgClick(DimensionForTimeBean.DimensionRelationBean.DimensionNameBean bean);
    }
}
