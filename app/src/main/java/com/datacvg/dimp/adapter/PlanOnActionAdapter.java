package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.TaskInfoBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-22
 * @Description : 行动计划
 */
public class PlanOnActionAdapter extends RecyclerView.Adapter<PlanOnActionAdapter.ViewHolder> {
    private Context mContext ;
    private List<TaskInfoBean.PlanBean.PlanDetailBean> planDetailBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;

    public PlanOnActionAdapter(Context mContext, List<TaskInfoBean.PlanBean.PlanDetailBean> planDetailBeans) {
        this.mContext = mContext;
        this.planDetailBeans = planDetailBeans ;
        inflater = LayoutInflater.from(mContext) ;
    }

    @NonNull
    @Override
    public PlanOnActionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_task_plan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanOnActionAdapter.ViewHolder holder, int position) {
        holder.tvUser.setText(planDetailBeans.get(position).getPlan_title());
        holder.tvTime.setText(mContext.getResources().getString(R.string.commencement_date).replace("#1"
                ,planDetailBeans.get(position).getStart_date() + "——" + planDetailBeans.get(position).getEnd_date()));
        holder.tvDetail.setText(mContext.getResources().getString(R.string.plan_details)
                .replace("#1",planDetailBeans.get(position).getPlan()));
    }

    @Override
    public int getItemCount() {
        return planDetailBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_user)
        TextView tvUser ;
        @BindView(R.id.tv_time)
        TextView tvTime ;
        @BindView(R.id.tv_detail)
        TextView tvDetail ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
