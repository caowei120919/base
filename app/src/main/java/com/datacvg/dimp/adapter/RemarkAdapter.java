package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.RemarkBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-02
 * @Description :
 */
public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<RemarkBean> remarkBeans = new ArrayList<>() ;

    public RemarkAdapter(Context mContext, List<RemarkBean> remarkBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.remarkBeans = remarkBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_remark,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RemarkBean bean = remarkBeans.get(position);
        holder.tvUserName.setText(position + 1 + bean.getUser_name());
        holder.tvText.setText(bean.getRemark());
        holder.tvCreateTime.setText(bean.getCreate_time());
    }

    @Override
    public int getItemCount() {
        return remarkBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_userName)
        TextView tvUserName ;
        @BindView(R.id.tv_text)
        TextView tvText ;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
