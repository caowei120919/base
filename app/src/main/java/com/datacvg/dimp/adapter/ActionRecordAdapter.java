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
 * @Description :
 */
public class ActionRecordAdapter extends RecyclerView.Adapter<ActionRecordAdapter.ViewHolder> {
    private Context mContext ;
    private List<TaskInfoBean.DetailBean> detailBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;

    public ActionRecordAdapter(Context mContext, List<TaskInfoBean.DetailBean> detailBeans) {
        this.mContext = mContext;
        this.detailBeans = detailBeans;
        this.inflater = LayoutInflater.from(mContext) ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_action_record,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskInfoBean.DetailBean detailBean = detailBeans.get(position);
        holder.tvRecordTime.setText(detailBean.getTask_text_time());
        holder.tvRecordUserName.setText(detailBean.getUser_name());
        holder.tvRecordState.setText(mContext.getResources().getString(R.string.description).replace("#1", detailBean.getTask_text()));
        holder.tvRecordOperate.setText(mContext.getResources().getString(R.string.operant_hehavior).replace("#1", detailBean.getState_desc()));
    }

    @Override
    public int getItemCount() {
        return detailBeans.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_recordTime)
        TextView tvRecordTime ;
        @BindView(R.id.tv_recordUserName)
        TextView tvRecordUserName ;
        @BindView(R.id.tv_recordOperate)
        TextView tvRecordOperate ;
        @BindView(R.id.tv_recordState)
        TextView tvRecordState ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
