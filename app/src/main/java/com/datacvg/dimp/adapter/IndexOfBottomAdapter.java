package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.IndexDetailListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class IndexOfBottomAdapter extends RecyclerView.Adapter<IndexOfBottomAdapter.ViewHolder> {
    private Context mContext ;
    private List<IndexDetailListBean> indexDetailListBeans = new ArrayList<>() ;
    private List<IndexChartBean> indexChartBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private IndexOfMineAdapter adapter ;


    public void setIndexDetailListBeans(List<IndexDetailListBean> indexDetailListBeans) {
        this.indexDetailListBeans = indexDetailListBeans;
        notifyDataSetChanged();
    }

    public IndexOfBottomAdapter(Context mContext, List<IndexDetailListBean> indexDetailListBeans) {
        this.mContext = mContext;
        this.indexDetailListBeans = indexDetailListBeans;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_index_for_title,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        indexChartBeans.clear();
        holder.tvTitleName.setText(indexDetailListBeans.get(position).getName());
        for (IndexChartBean bean :indexDetailListBeans.get(position).getDetail()){
            if (!bean.getSelected()){
                indexChartBeans.add(bean);
            }
        }
        adapter = new IndexOfMineAdapter(mContext,indexChartBeans);
        GridLayoutManager indexLayoutManager = new GridLayoutManager(mContext,3);
        holder.recycleIndex.setLayoutManager(indexLayoutManager);
        holder.recycleIndex.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return indexDetailListBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_titleName)
        TextView tvTitleName ;
        @BindView(R.id.recycle_index)
        RecyclerView recycleIndex ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
