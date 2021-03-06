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
import com.datacvg.dimp.bean.ScreenDetailBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :
 */
public class ScreenDetailAdapter extends RecyclerView.Adapter<ScreenDetailAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ScreenDetailBean.ListBean> beans = new ArrayList<>();
    private OnScreenDetailClick click ;


    public ScreenDetailAdapter(Context mContext, List<ScreenDetailBean.ListBean> beans
            ,OnScreenDetailClick click) {
        this.mContext = mContext;
        this.beans = beans;
        inflater = LayoutInflater.from(mContext);
        this.click = click ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_screen_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(beans.get(position).getImg_name());
        if(beans.get(position).getRes_type().equals("img")){
            holder.imgTitle.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_img_screen));
        }else{
            holder.imgTitle.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_screen_detail_title));
        }
        holder.imgDelete.setOnClickListener(view -> {
            click.onDeleteClick(position);
        });
        holder.imgSetting.setOnClickListener(view -> {
            click.onSettingClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_delete)
        ImageView imgDelete ;
        @BindView(R.id.img_setting)
        ImageView imgSetting ;
        @BindView(R.id.img_title)
        ImageView imgTitle ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 详情单个相关操作监听
     */
    public interface OnScreenDetailClick{
        /**
         * 删除
         * @param position
         */
        void onDeleteClick(int position);

        /**
         * 设置
         * @param position
         */
        void onSettingClick(int position);
    }
}
