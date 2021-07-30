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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.ContactComparator;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.PinYinUtils;
import com.datacvg.dimp.bean.Contact;
import com.datacvg.dimp.event.ContactEvent;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.controller.DbContactController;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


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
    private final int nearCount = 5 ;

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
            Contact contact = new Contact(beans.get(i),beans.get(i).getName(),beans.get(i).getChecked());
            map.put(pinyin, contact);
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();
        characterList.add(mContext.getResources().getString(R.string.nearest_contact));
        resultList.add(new Contact(mContext.getResources().getString(R.string.nearest_contact)
                , ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
        if(beans.size() > nearCount){
            for (int i = 0 ; i < nearCount ; i++){
                Contact contact = new Contact(beans.get(i),beans.get(i).getName(),beans.get(i).getChecked());
                contact.setmType(ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal());
                resultList.add(contact);
            }
        }else{
            for (int i = 0 ; i < beans.size() ; i++){
                Contact contact = new Contact(beans.get(i),beans.get(i).getName(),beans.get(i).getChecked());
                contact.setmType(ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal());
                resultList.add(contact);
            }
        }
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
            GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + Constants.HEAD_IMG_URL
                    + resultList.get(position).getBean().getId()
                    , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
            Glide.with(mContext).load(imgUrl)
                    .placeholder(R.mipmap.screen_default)
                    .error(R.mipmap.screen_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((ContactViewHolder) holder).imgAvatar);
            ((ContactViewHolder) holder).mTextView.setText(resultList.get(position).getName());
            ((ContactViewHolder) holder).tvContact.setSelected(resultList.get(position).isChecked());
            ((ContactViewHolder) holder).tvContact.setOnClickListener(view ->  {
                    ((ContactViewHolder) holder).tvContact.setSelected(!((ContactViewHolder) holder).tvContact.isSelected());
                    ContactBean contactBean = resultList.get(position).getBean();
                    contactBean.setChecked(((ContactViewHolder) holder).tvContact.isSelected());
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new ContactEvent(contactBean));
            });
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
        CircleImageView imgAvatar ;

        @BindView(R.id.tv_contact)
        TextView tvContact ;
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
