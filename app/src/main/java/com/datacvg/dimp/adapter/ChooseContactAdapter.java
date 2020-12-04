package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.Contact;
import com.datacvg.dimp.event.HeadOrAssistantEvent;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbContactController;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;
import com.datacvg.dimp.widget.DividerItemDecoration;

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
public class ChooseContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<DepartmentBean> defaultUserBeans = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();
    private List<Contact> editContacts = new ArrayList<>();
    private int chooseType = Constants.CHOOSE_TYPE_HEAD ;

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public void setChooseType(int chooseType) {
        this.chooseType = chooseType;
        contacts.clear();
        handleContact();
        notifyDataSetChanged();
    }

    public ChooseContactAdapter(Context mContext, int chooseType) {
        this.chooseType = chooseType ;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        handleContact();
    }

    /**
     * 重组联系人数据
     */
    private void handleContact() {
        defaultUserBeans = DbDepartmentController.getInstance(mContext).queryDepartmentList();
        for (DepartmentBean departmentBean : defaultUserBeans){
            Contact contact = new Contact(departmentBean.getD_res_clname()
                    ,ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal());
            contact.setDepartmentId(departmentBean.getD_res_pkid());
            contacts.add(contact);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()){
                return new CharacterViewHolder(inflater
                        .inflate(R.layout.item_choose_character,parent,false));
            }else{
                return new ContactViewHolder(inflater
                        .inflate(R.layout.item_choose_contact,parent,false));
            }
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position).getmType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CharacterViewHolder){
            holderCharacter((CharacterViewHolder)holder,position);
        }else{
            holderContact((ContactViewHolder)holder,position);
        }
    }

    private void holderContact(ContactViewHolder holder, int position) {
        holder.mTextView.setText(contacts.get(position).getBean().getName());
        holder.cbContact.setOnCheckedChangeListener(null);
        if(chooseType == Constants.CHOOSE_TYPE_HEAD){
            holder.cbContact.setClickable(!contacts.get(position).getBean().isAssistantChecked());
            holder.cbContact.setChecked(contacts.get(position).getBean().isHeadChecked());
        }else{
            holder.cbContact.setChecked(contacts.get(position).getBean().isAssistantChecked());
            holder.cbContact.setClickable(!contacts.get(position).getBean().isHeadChecked());
        }
        holder.mTextView.setTextColor(holder.cbContact.isClickable()
                ? mContext.getResources().getColor(R.color.c_666666)
                : mContext.getResources().getColor(R.color.c_999999));
        holder.cbContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chooseType == Constants.CHOOSE_TYPE_HEAD){
                    if(b){
                        for (Contact contact : contacts){
                            contact.setChecked(false);
                            if(contact.getBean() != null){
                                ContactBean contactBean = contact.getBean();
                                contactBean.setHeadChecked(false);
                                DbContactController.getInstance(mContext).updateContact(contactBean);
                            }
                        }
                        contacts.get(position).setChecked(true);
                        ContactBean contactBean = contacts.get(position).getBean();
                        contactBean.setHeadChecked(true);
                        DbContactController.getInstance(mContext).updateContact(contactBean);
                        notifyDataSetChanged();
                    }
                }else{
                    ContactBean contactBean = contacts.get(position).getBean();
                    contactBean.setAssistantChecked(b);
                    DbContactController.getInstance(mContext).updateContact(contactBean);
                }
                EventBus.getDefault().post(new HeadOrAssistantEvent(contacts.get(position),b,chooseType));
            }
        });
    }

    private void holderCharacter(CharacterViewHolder holder, int position) {
        holder.mTextView.setText(contacts.get(position).getName());
        holder.cbContact.setOnCheckedChangeListener(null);
        holder.cbContact.setChecked(contacts.get(position).isExpend());
        holder.cbContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editContacts.clear();
                if(b){
                    for (ContactBean contactBean
                            : DbContactController.getInstance(mContext)
                            .queryContactList(contacts.get(position).getDepartmentId())){
                        Contact contactUser = new Contact(contactBean,ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal());
                        contactUser.setDepartmentId(contacts.get(position).getDepartmentId());
                        editContacts.add(contactUser);
                    }
                    contacts.addAll(position + 1,editContacts);
                }else{
                    for (Contact contactBean : contacts){
                        if(contactBean.getBean() != null){
                            if(contactBean.getDepartmentId().equals(contacts.get(position).getDepartmentId())){
                                editContacts.add(contactBean);
                            }
                        }
                    }
                    contacts.removeAll(editContacts);
                }
                contacts.get(position).setExpend(b);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.contact_name)
        TextView mTextView ;
        @BindView(R.id.cb_contact)
        CheckBox cbContact ;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.contact_name)
        TextView mTextView ;
        @BindView(R.id.cb_contact)
        CheckBox cbContact ;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
