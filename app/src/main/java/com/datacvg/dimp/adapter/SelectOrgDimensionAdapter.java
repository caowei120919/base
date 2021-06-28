package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.DimensionBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-04-16
 * @Description : 组织维度适配器
 */
public class SelectOrgDimensionAdapter extends RecyclerView.Adapter<SelectOrgDimensionAdapter.ViewHolder> {

    private Context mContext ;
    private List<DimensionBean> orgIndexDimensionBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private SelectOrgClickListener clickListener ;

    public SelectOrgDimensionAdapter(Context mContext
            , List<DimensionBean> orgIndexDimensionBeans, SelectOrgClickListener clickListener) {
        this.mContext = mContext;
        this.orgIndexDimensionBeans = orgIndexDimensionBeans;
        this.clickListener = clickListener ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_filter_dimension,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DimensionBean bean = orgIndexDimensionBeans.get(position);
        holder.itemView.setPadding(Integer.valueOf(bean.getRes_level()) * 35,0,0,0 );
        holder.tvDimension.setText(bean.getD_res_name());
        holder.imgDimension.setImageBitmap(BitmapFactory
                .decodeResource(mContext.getResources()
                        ,bean.getOpen() ? R.mipmap.drag_up : R.mipmap.drag_down));
        holder.imgDimension.setVisibility((bean.getNodes() != null && bean.getNodes().size() > 0)
                ? View.VISIBLE : View.INVISIBLE);
        if(bean.getNodes() != null && bean.getNodes().size() > 0){
            holder.imgDimension.setOnClickListener(v -> {
                if(!bean.getOpen()){
                    bean.setOpen(true);
                    orgIndexDimensionBeans.addAll(position + 1 ,bean.getNodes());
                }else{
                    bean.setOpen(false);
                    removeAllNodes(bean);
                }
                notifyDataSetChanged();
            });
        }
        holder.itemView.setOnClickListener(v -> {
            clickListener.onSelectOrgClick(orgIndexDimensionBeans.get(position));
        });
    }

    /**
     * 移除下面所有子列表信息
     * @param bean
     */
    private void removeAllNodes(DimensionBean bean) {
        for (DimensionBean childBean : bean.getNodes()) {
            if(childBean.getNodes() != null && childBean.getNodes().size() > 0){
                removeAllNodes(childBean);
            }
            childBean.setOpen(false);
            orgIndexDimensionBeans.remove(childBean);
        }
    }

    @Override
    public int getItemCount() {
        return orgIndexDimensionBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_dimension)
        ImageView imgDimension ;

        @BindView(R.id.tv_dimension)
        TextView tvDimension ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface SelectOrgClickListener{
        void onSelectOrgClick(DimensionBean bean);
    }
}
