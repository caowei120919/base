package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-15
 * @Description :
 */
public class SearchContactOrDepartmentAdapter extends RecyclerView.Adapter<SearchContactOrDepartmentAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ContactOrDepartmentForActionBean> contactOrDepartmentBeans = new ArrayList<>() ;

    public SearchContactOrDepartmentAdapter(Context mContext, List<ContactOrDepartmentForActionBean> contactOrDepartmentBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext) ;
        this.contactOrDepartmentBeans = contactOrDepartmentBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_contact_or_department,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactOrDepartmentForActionBean contactOrDepartmentBean = contactOrDepartmentBeans.get(position);
        holder.cbDepartment.setSelected(contactOrDepartmentBean.isChecked());
        holder.cbDepartment.setOnClickListener(v -> {
            contactOrDepartmentBean.setChecked(!contactOrDepartmentBean.isChecked());
            notifyDataSetChanged();
        });
        holder.tvDepartmentName.setText(contactOrDepartmentBean.getContactOrDepartmentBean().getName());
    }

    @Override
    public int getItemCount() {
        return contactOrDepartmentBeans.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
