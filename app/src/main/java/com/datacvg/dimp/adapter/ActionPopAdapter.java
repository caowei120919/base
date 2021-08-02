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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.ActionForIndexBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-02
 * @Description :
 */
public class ActionPopAdapter extends RecyclerView.Adapter<ActionPopAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ActionForIndexBean.ActionIndexBean> actionIndexBeans = new ArrayList<>() ;

    public ActionPopAdapter(Context mContext, List<ActionForIndexBean.ActionIndexBean> actionIndexBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.actionIndexBeans = actionIndexBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pop_action,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionForIndexBean.ActionIndexBean bean = actionIndexBeans.get(position);
        if(bean == null){
            return;
        }
        GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + Constants.HEAD_IMG_URL
                + bean.getCreate_user_pkid()
                , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
        Glide.with(mContext).load(imgUrl).into(holder.circleHead);
        holder.tvName.setText(bean.getCreate_user_name());
        switch (bean.getTask_priority()){
            case 1 :
                holder.imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_high));
                break;

            case 2 :
                holder.imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_mid));
                break;

            case 3 :
                holder.imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_low));
                break;
        }
        switch (bean.getTask_state()){
            case "3" :
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_processing));
                break;

            case "6" :
            case "7" :
            case "8" :
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_complete));
                break;

            case "9" :
            case "11" :
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_undo));
                break;

            default:
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_wait_receive));
                break;
        }
        holder.tvText.setText(bean.getTask_text().replace("[task]",""));
        holder.tvTittle.setText(bean.getTask_title());
        holder.tvCreateTime.setText(mContext.getResources().getString(R.string.creation_time)
                + ":" + (bean.getCreate_time().length() > 10 ? bean.getCreate_time().substring(0,10) : bean.getCreate_time()));
        holder.tvEndTime.setText(mContext.getResources().getString(R.string.by_the_time)
                + ":" + (bean.getTask_deadline().length() > 10 ? bean.getTask_deadline().substring(0,10) : bean.getTask_deadline()));
    }

    @Override
    public int getItemCount() {
        return actionIndexBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circle_head)
        CircleImageView circleHead ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.tv_text)
        TextView tvText ;
        @BindView(R.id.img_state)
        ImageView imgState ;
        @BindView(R.id.tv_tittle)
        TextView tvTittle ;
        @BindView(R.id.img_level)
        ImageView imgLevel ;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime ;
        @BindView(R.id.tv_endTime)
        TextView tvEndTime ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
