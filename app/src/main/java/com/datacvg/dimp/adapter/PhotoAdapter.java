package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.datacvg.dimp.R;
import com.datacvg.dimp.event.DeletePhotoEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-17
 * @Description :
 */
public class PhotoAdapter extends BaseAdapter {
    private Context mContext ;
    private List<String> photoPaths = new ArrayList<>();

    public PhotoAdapter(Context mContext, List<String> photoPaths) {
        this.mContext = mContext;
        this.photoPaths = photoPaths;
    }

    @Override
    public int getCount() {
        return photoPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return photoPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_photo,null);
            viewHolder = new ViewHolder();
            viewHolder.imgPicture = convertView.findViewById(R.id.img_photo);
            viewHolder.imgDelete = convertView.findViewById(R.id.img_delete) ;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(photoPaths.get(position))
                .placeholder(R.mipmap.screen_default)
                .error(R.mipmap.screen_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.imgPicture);
        viewHolder.imgDelete.setOnClickListener(v -> {
            EventBus.getDefault().post(new DeletePhotoEvent(position));
        });
        return convertView;
    }

    public class ViewHolder{
        ImageView imgPicture ;
        ImageView imgDelete ;
    }
}
