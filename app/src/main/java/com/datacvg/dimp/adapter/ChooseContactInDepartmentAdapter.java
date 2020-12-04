package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.DefaultUserBean;
import com.datacvg.dimp.greendao.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-03
 * @Description :
 */
public class  ChooseContactInDepartmentAdapter
        extends RecyclerView.Adapter<ChooseContactInDepartmentAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ContactBean> userBeans = new ArrayList<>();

    public ChooseContactInDepartmentAdapter(Context mContext, List<ContactBean> userBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.userBeans = userBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_choose_contact_in_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.contactName.setText(userBeans.get(position).getName());
        holder.cbContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                userBeans.get(position).setChecked(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cb_contact)
        CheckBox cbContact ;
        @BindView(R.id.contact_name)
        TextView contactName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
