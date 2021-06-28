package com.datacvg.dimp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.event.AddOrRemoveIndexEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description : 我的指标
 */
public class IndexOfMineAdapter extends RecyclerView.Adapter<IndexOfMineAdapter.ViewHolder> {
    private Context mContext ;
    private List<IndexChartBean> indexChartBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;

    public IndexOfMineAdapter(Context mContext, List<IndexChartBean> indexChartBeans) {
        this.mContext = mContext;
        this.indexChartBeans = indexChartBeans;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_add_index,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndexChartBean bean = indexChartBeans.get(position);
        if(bean.getChart_high().equals("1") && bean.getChart_wide().equals("1")){
            holder.relContain.setBackground(mContext.getDrawable(R.drawable.shape_one_and_one_top));
        }else if(bean.getChart_high().equals("1") && bean.getChart_wide().equals("2")){
            holder.relContain.setBackground(mContext.getDrawable(R.drawable.shape_two_and_one_top));
        }else{
            holder.relContain.setBackground(mContext.getDrawable(R.drawable.shape_two_and_two_top));
        }
        if(TextUtils.isEmpty(bean.getName())){
            holder.tvIndexName.setText(bean.getIndex_clname());
        }else{
            holder.tvIndexName.setText(bean.getName());
        }

        holder.itemView.setOnClickListener(view -> {
            EventBus.getDefault().post(new AddOrRemoveIndexEvent(bean));
        });
    }

    @Override
    public int getItemCount() {
        return indexChartBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rel_contain)
        RelativeLayout relContain ;
        @BindView(R.id.tv_indexName)
        TextView tvIndexName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
