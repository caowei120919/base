package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.TaskInfoBean;

import java.util.ArrayList;
import java.util.List;

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
        this.planDetailBeans = planDetailBeans;
        inflater = LayoutInflater.from(mContext) ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return planDetailBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
