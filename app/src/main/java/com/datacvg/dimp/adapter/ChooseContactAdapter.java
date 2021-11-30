package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.ChooseContactForActionBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-10
 * @Description :
 */
public class ChooseContactAdapter extends RecyclerView.Adapter<ChooseContactAdapter.ViewHolder> {
    private Context mContext ;

    public ChooseContactAdapter(Context mContext, List<ChooseContactForActionBean> chooseContactForActionBeans,List<ChooseContactForActionBean> allBeans) {
        this.mContext = mContext;
        this.chooseContactForActionBeans = chooseContactForActionBeans;
        this.allBeans = allBeans ;
    }

    private List<ChooseContactForActionBean> chooseContactForActionBeans = new ArrayList<>() ;
    private List<ChooseContactForActionBean> allBeans = new ArrayList<>() ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_concact_from_action,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChooseContactForActionBean chooseContactForActionBean = chooseContactForActionBeans.get(position);
        holder.cbExpend.setVisibility(chooseContactForActionBean.getContact() ? View.GONE : View.VISIBLE);
        holder.tvDepartmentName.setText(chooseContactForActionBean.getName());
        holder.cbExpend.setOnClickListener(v -> {
            if (!chooseContactForActionBean.getChildBeans().isEmpty()){
                chooseContactForActionBeans.addAll(position + 1 ,chooseContactForActionBean.getChildBeans());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chooseContactForActionBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_expend)
        CheckBox cbExpend ;

        @BindView(R.id.cb_department)
        ImageView cbDepartment ;

        @BindView(R.id.tv_departmentName)
        TextView tvDepartmentName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
