package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.UserJobsBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-19
 * @Description :职位选择适配器
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<UserJobsBean> userJobsBeans = new ArrayList<>() ;
    private OnJobChangeListener listener ;

    public JobAdapter(Context mContext,OnJobChangeListener listener,
                      List<UserJobsBean> userJobsBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listener = listener ;
        this.userJobsBeans = userJobsBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_job_name,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserJobsBean bean = userJobsBeans.get(position);
        holder.tvName.setText(bean.getPost_clname());
        if(bean.getUser_pkid().equals(PreferencesHelper.get(Constants.USER_PKID,""))){
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.c_303030));
        }else{
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.c_666666));
        }
        holder.tvName.setOnClickListener(v -> {
            PLog.e("岗位切换操作");
            listener.onJobChangeListener(bean);
        });
    }

    @Override
    public int getItemCount() {
        return userJobsBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 岗位切换事件监听
     */
    public interface OnJobChangeListener{
        void onJobChangeListener(UserJobsBean userJobsBean);
    }
}
