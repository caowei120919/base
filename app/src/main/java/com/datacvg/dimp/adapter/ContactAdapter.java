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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.ContactComparator;
import com.datacvg.dimp.baseandroid.utils.PinYinUtils;
import com.datacvg.dimp.bean.Contact;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-11
 * @Description :
 */
public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ContactBean> beans = new ArrayList<>();
    private Context mContext ;
    private List<String> mContactList = new ArrayList<>();
    private List<Contact> resultList = new ArrayList<>();
    private List<String> characterList = new ArrayList<>();

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public ContactAdapter(Context context, List<ContactBean> beans) {
        mContext = context;
        this.beans = beans;
        handleContact();
    }

    private void handleContact() {
        mContactList = new ArrayList<>();
        Map<String, Contact> map = new HashMap<>();

        for (int i = 0; i < beans.size(); i++) {
            String pinyin = PinYinUtils.getPingYin(beans.get(i).getName());
            Contact contact = new Contact(beans.get(i),beans.get(i).getName());
            map.put(pinyin, contact);
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();

        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) {
                    characterList.add(character);
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            Contact contact = map.get(name) ;
            contact.setmType(ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal());
            resultList.add(contact);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_character, parent, false));
        } else {
            return new ContactViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_contact, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterViewHolder) {
            ((CharacterViewHolder) holder).mTextView.setText(resultList.get(position).getName());
        } else if (holder instanceof ContactViewHolder) {
            String url = "http://dimp.dev.datacvg.com/api/mobile/"
                    + "login/readheadimg?userPkid=" + resultList.get(position).getBean().getId();
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.screen_default)
                    .error(R.mipmap.screen_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((ContactViewHolder) holder).imgAvatar);
            ((ContactViewHolder) holder).mTextView.setText(resultList.get(position).getName()
                    + "(" + DbDepartmentController.getInstance(mContext)
                    .getDepartmentNameForDepartmentId(resultList.get(position)
                            .getBean().getDepartment_id()) + ")");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.contact_name)
        TextView mTextView ;

        @BindView(R.id.img_avatar)
        ImageView imgAvatar ;

        @BindView(R.id.cb_contact)
        CheckBox cbContact ;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.character)
        TextView mTextView ;
        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
