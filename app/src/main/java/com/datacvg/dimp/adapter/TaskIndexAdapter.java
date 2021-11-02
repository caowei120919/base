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
import com.datacvg.dimp.bean.ActionPlanIndexBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-02
 * @Description :
 */
public class TaskIndexAdapter extends RecyclerView.Adapter<TaskIndexAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ActionPlanIndexBean> actionPlanIndexBeans = new ArrayList<>() ;

    public TaskIndexAdapter(Context mContext, List<ActionPlanIndexBean> actionPlanIndexBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext) ;
        this.actionPlanIndexBeans = actionPlanIndexBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_index_action,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionPlanIndexBean actionPlanIndexBean = actionPlanIndexBeans.get(position) ;
        if(actionPlanIndexBean == null){
            return;
        }
        holder.itemView.setPadding(Integer.valueOf(actionPlanIndexBean.getLevel()) * 35,0,0,0 );
        holder.imgDash.setVisibility(actionPlanIndexBean.getChildBeans().isEmpty() ? View.INVISIBLE : View.VISIBLE);
        holder.imgDash.setImageBitmap(actionPlanIndexBean.getSpread() ? BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.dash_index_report_down)
                : BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.dash_index_report_up));
        holder.imgDash.setOnClickListener(v -> {
            actionPlanIndexBean.setSpread(!actionPlanIndexBean.getSpread());
            if(actionPlanIndexBean.getSpread()){
                actionPlanIndexBeans.addAll(position + 1,actionPlanIndexBean.getChildBeans());
            }else{
                actionPlanIndexBeans.removeAll(actionPlanIndexBean.getChildBeans());
            }
            notifyDataSetChanged();
        });
        holder.imgSelected.setImageBitmap(actionPlanIndexBean.isChecked() ? BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.cx_select)
                : BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.cx_unselect));
        holder.imgSelected.setOnClickListener(v -> {
            actionPlanIndexBean.setChecked(!actionPlanIndexBean.isChecked());
            EventBus.getDefault().post(actionPlanIndexBean);
            if(actionPlanIndexBean.isChecked()){
                holder.imgSelected.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.cx_select));
            }else{
                holder.imgSelected.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.cx_unselect));
            }
        });
        holder.tvIndexName.setText(actionPlanIndexBean.getName());
    }

    @Override
    public int getItemCount() {
        return actionPlanIndexBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_dash)
        ImageView imgDash ;
        @BindView(R.id.img_selected)
        ImageView imgSelected ;
        @BindView(R.id.tv_indexName)
        TextView tvIndexName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
