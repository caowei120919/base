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
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.SearchIndexBean;
import com.datacvg.dimp.event.SearchIndexBeanClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-14
 * @Description : 搜索指标适配器
 */
public class SearchIndexAdapter extends RecyclerView.Adapter<SearchIndexAdapter.ViewHolder> {
    private Context mContext ;
    private List<IndexChartBean> indexChartBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;

    public SearchIndexAdapter(Context mContext, List<IndexChartBean> indexChartBeans) {
        this.mContext = mContext;
        this.indexChartBeans = indexChartBeans;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_index,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndexChartBean chartBean = indexChartBeans.get(position);
        holder.tvIndexName.setText(LanguageUtils.isZh(mContext)
                ? chartBean.getIndex_clname() : chartBean.getIndex_flname());
        holder.cbSelect.setChecked(chartBean.getSelected());
        if(chartBean.getChart_high().equals("1") && chartBean.getChart_wide().equals("1")){
            holder.tvIndexType.setBackground(mContext.getDrawable(R.drawable.shape_one_and_one));
            holder.tvIndexType.setText("1 * 1");
        }else if(chartBean.getChart_high().equals("1") && chartBean.getChart_wide().equals("2")){
            holder.tvIndexType.setBackground(mContext.getDrawable(R.drawable.shape_two_and_one));
            holder.tvIndexType.setText("2 * 1");
        }else{
            holder.tvIndexType.setBackground(mContext.getDrawable(R.drawable.shape_two_and_two));
            holder.tvIndexType.setText("2 * 2");
        }
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chartBean.setSelected(b);
                EventBus.getDefault().post(new SearchIndexBeanClickEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return indexChartBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_indexType)
        TextView tvIndexType ;
        @BindView(R.id.cb_select)
        CheckBox cbSelect ;
        @BindView(R.id.tv_indexName)
        TextView tvIndexName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
