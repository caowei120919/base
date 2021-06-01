package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.IndexChartBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-13
 * @Description :
 */
public class IndexForDimensionAdapter extends RecyclerView.Adapter<IndexForDimensionAdapter.ViewHolder> {
    private Context mContext ;
    private List<IndexChartBean> detailBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private OnIndexCheckedClick checkedClick ;

    public IndexForDimensionAdapter(Context mContext
            , List<IndexChartBean> detailBeans
            , OnIndexCheckedClick checkedClick) {
        this.mContext = mContext;
        this.detailBeans = detailBeans;
        inflater = LayoutInflater.from(mContext);
        this.checkedClick = checkedClick ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.item_index_for_dimension,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndexChartBean detailBean = detailBeans.get(position);
        if(detailBean.getChart_high().equals("1")){
            if(detailBean.getChart_wide().equals("1")){
                holder.tvTypeIndex.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_one_and_one));
                holder.tvTypeIndex.setText("1 * 1");
            }else{
                holder.tvTypeIndex.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_two_and_one));
                holder.tvTypeIndex.setText("2 * 1");
            }
        }else{
            holder.tvTypeIndex.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_two_and_two));
            holder.tvTypeIndex.setText("2 * 2");
        }

        holder.tvIndexName.setText(detailBean.getIndex_clname());
        holder.checkIndex.setSelected(detailBean.getSelected());
        holder.checkIndex.setOnClickListener(v -> {
            detailBean.setSelected(!detailBean.getSelected());
            checkedClick.onIndexClick(detailBean.getSelected());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return detailBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_widthAndHigh)
        TextView tvTypeIndex ;
        @BindView(R.id.tv_indexName)
        TextView tvIndexName ;
        @BindView(R.id.check_index)
        TextView checkIndex ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnIndexCheckedClick{
        void onIndexClick(boolean isSelected) ;
    }
}
