package com.datacvg.sempmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.bean.IndexBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-30
 * @Description :
 */
public class MyIndexAdapter extends RecyclerView.Adapter<MyIndexAdapter.ViewHolder> {
    private Context mContext ;
    private List<IndexBean.MyListBean> myListBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnMyIndexClickListener listener ;

    public MyIndexAdapter(Context mContext, List<IndexBean.MyListBean> myListBeans
            ,OnMyIndexClickListener listener) {
        this.mContext = mContext;
        this.myListBeans = myListBeans;
        inflater = LayoutInflater.from(mContext);
        this.listener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_index,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndexBean.MyListBean bean = myListBeans.get(position);
        holder.tvIndex.setText(bean.getTitle());
        switch (bean.getSize_x() * bean.getSize_y()){
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
            listener.onMyIndexClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return myListBeans.size();
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
    public interface OnMyIndexClickListener{
        /**
         * 我的指标点击
         * @param position
         */
        void onMyIndexClick(int position) ;
    }
}
