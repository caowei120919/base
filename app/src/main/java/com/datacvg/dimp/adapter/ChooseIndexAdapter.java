package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * @Time : 2020-12-03
 * @Description :
 */
public class ChooseIndexAdapter extends RecyclerView.Adapter<ChooseIndexAdapter.ContactChooseViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ActionPlanIndexBean> actionPlanIndexBeans = new ArrayList<>();

    public ChooseIndexAdapter(Context mContext, List<ActionPlanIndexBean> contactBeans) {
        this.mContext = mContext ;
        inflater = LayoutInflater.from(mContext);
        this.actionPlanIndexBeans = contactBeans ;
    }

    @NonNull
    @Override
    public ChooseIndexAdapter.ContactChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactChooseViewHolder(inflater
                .inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseIndexAdapter.ContactChooseViewHolder holder, int position) {
        holder.imgAvatar.setVisibility(View.GONE);
        holder.mTextView.setText(actionPlanIndexBeans.get(position).getName());
        holder.cbContact.setChecked(actionPlanIndexBeans.get(position).isChecked());
        holder.cbContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                actionPlanIndexBeans.get(position).setChecked(b);
                EventBus.getDefault().post(actionPlanIndexBeans.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return actionPlanIndexBeans.size();
    }

    public class ContactChooseViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.contact_name)
        TextView mTextView ;

        @BindView(R.id.img_avatar)
        ImageView imgAvatar ;

        @BindView(R.id.cb_contact)
        CheckBox cbContact ;
        public ContactChooseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
