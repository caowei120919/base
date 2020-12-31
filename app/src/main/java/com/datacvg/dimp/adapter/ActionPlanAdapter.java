package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ActionPlanBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-21
 * @Description : 行动方案
 */
public class ActionPlanAdapter extends RecyclerView.Adapter<ActionPlanAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ActionPlanBean> actionPlanBeans = new ArrayList<>() ;
    private ToActionClick click ;

    public ActionPlanAdapter(Context mContext, List<ActionPlanBean> actionPlanBeans
            ,ToActionClick click) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.actionPlanBeans = actionPlanBeans;
        this.click = click ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_action_plan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionPlanBean bean = actionPlanBeans.get(position);
        GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + bean.getImgurl()
                , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
        Glide.with(mContext).load(imgUrl).into(holder.circleHead);
        holder.tvName.setText(bean.getCreate_user_name());
        holder.tvState.setText(bean.getState_desc());
        switch (bean.getPriority()){
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
        switch (bean.getState()){
            case 3 :
                    holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_processing));
                break;

            case 6 :
            case 7 :
            case 8 :
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_complete));
                break;

            case 9 :
            case 11 :
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_undo));
                break;

            default:
                holder.imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_wait_receive));
                break;
        }
        holder.tvTittle.setText(bean.getTitle());
        holder.tvCreateTime.setText(mContext.getResources().getString(R.string.creation_time)
                + ":" + bean.getCreate_time());
        holder.tvEndTime.setText(mContext.getResources().getString(R.string.by_the_time)
                + ":" +bean.getDeadline());
        holder.tvContent.setText(bean.getText());
        holder.itemView.setOnClickListener(view -> {
            PLog.e("查看详情");
            click.goActionDetailClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return actionPlanBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circle_head)
        CircleImageView circleHead ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.tv_state)
        TextView tvState ;
        @BindView(R.id.img_state)
        ImageView imgState ;
        @BindView(R.id.img_level)
        ImageView imgLevel ;
        @BindView(R.id.tv_tittle)
        TextView tvTittle ;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime ;
        @BindView(R.id.tv_endTime)
        TextView tvEndTime ;
        @BindView(R.id.rel_content)
        RelativeLayout relContent ;
        @BindView(R.id.tv_content)
        TextView tvContent ;
        @BindView(R.id.tv_details)
        TextView tvDetails ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ToActionClick{
        /**
         * 查看行动方案详情
         * @param position
         */
        void goActionDetailClick(int position);
    }
}
