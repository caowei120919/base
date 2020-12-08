package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description : 维度选择pop内部适配器
 */
public class DimensionPopAdapter extends RecyclerView.Adapter<DimensionPopAdapter.ViewHolder> {
    private List<DimensionBean> dimensionBeans = new ArrayList<>();
    private Context mContext ;
    private LayoutInflater inflater ;
    private DimensionCheckListener listener ;
    private DimensionType type ;

    public DimensionPopAdapter(Context mContext, List<DimensionBean> dimensionBeans
            , DimensionCheckListener listener) {
        this.dimensionBeans = dimensionBeans;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pop_dimension,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(dimensionBeans.get(position).getText());
        if(dimensionBeans.get(position).getNodes() != null){
            holder.cbOpenNodes.setVisibility(View.VISIBLE);
        }else{
            holder.cbOpenNodes.setVisibility(View.INVISIBLE);
        }
        holder.cbOpenNodes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(dimensionBeans.get(position).getNodes() != null){
                        dimensionBeans.addAll(dimensionBeans.get(position).getNodes());
                    }
                }else{
                    dimensionBeans.removeAll(dimensionBeans.get(position).getNodes());
                }
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(view -> {
            listener.OnClick(position,type);
        });
    }

    public void setType(DimensionType type) {
        this.type = type;
    }

    @Override
    public int getItemCount() {
        return dimensionBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.cb_openNodes)
        CheckBox cbOpenNodes ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface DimensionCheckListener{
        /**
         * 单项点击时间
         * @param position
         * @param type
         */
        void OnClick(int position, DimensionType type);
    }
}
