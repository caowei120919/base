package com.datacvg.dimp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.IndexBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-03
 * @Description :
 */
public class OtherIndexAdapter extends RecyclerView.Adapter<OtherIndexAdapter.ViewHolder> {
    private Context mContext ;
    private List<IndexBean.EndIndexBean> endResultBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnOtherIndexClickListener otherIndexClickListener ;

    public OtherIndexAdapter(Context mContext, List<IndexBean.EndIndexBean> endResultBeans
            ,OnOtherIndexClickListener otherIndexClickListener) {
        this.mContext = mContext;
        this.endResultBeans = endResultBeans;
        this.otherIndexClickListener = otherIndexClickListener ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_index,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndexBean.EndIndexBean bean = endResultBeans.get(position);
        holder.tvIndex.setText(TextUtils.isEmpty(bean.getChart_top_title())
                ? bean.getIndex_clname() : bean.getChart_top_title());
        switch (Integer.valueOf(bean.getChart_high()) * Integer.valueOf(bean.getChart_wide())){
            /**
             * 1*1
             */
            case 1 :
                holder.tvIndex.setBackground(mContext.getResources()
                        .getDrawable(R.drawable.shape_one_and_one_bg));
                holder.tvIndexCircle
                        .setBackgroundColor(mContext.getResources().getColor(R.color.c_4bbba8));
                break;

            /**
             * 2*1
             */
            case 2 :
                holder.tvIndex.setBackground(mContext.getResources()
                        .getDrawable(R.drawable.shape_two_and_one_bg));
                holder.tvIndexCircle
                        .setBackgroundColor(mContext.getResources().getColor(R.color.c_c79f50));
                break;

            /**
             * 2*2
             */
            case 4 :
                holder.tvIndex.setBackground(mContext.getResources()
                        .getDrawable(R.drawable.shape_two_and_two_bg));
                holder.tvIndexCircle
                        .setBackgroundColor(mContext.getResources().getColor(R.color.c_bf5053));
                break;
        }

        holder.itemView.setOnClickListener(view -> {
            otherIndexClickListener.onOtherIndexClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return endResultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_index)
        TextView tvIndex ;
        @BindView(R.id.tv_indexCircle)
        TextView tvIndexCircle ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 对外暴露指标点击事件
     */
    public interface OnOtherIndexClickListener{
        /**
         * 其他指标被点击
         * @param position
         */
        void onOtherIndexClick(int position) ;
    }
}
