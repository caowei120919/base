package com.datacvg.dimp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.event.DeleteCommentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-07
 * @Description :
 */
public class CommentPictureAdapter extends RecyclerView.Adapter<CommentPictureAdapter.ViewHolder> {
    private List<String> imageList = new ArrayList<>();
    private Context mContext ;
    private LayoutInflater inflater ;

    public CommentPictureAdapter(Context mContext,List<String> imageList) {
        this.imageList = imageList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comment_picture,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgUrl = Constants.BASE_URL + Constants.IMG_URL_NONE + imageList.get(position);
        Glide.with(mContext).load(imgUrl)
                .placeholder(R.mipmap.screen_default)
                .error(R.mipmap.screen_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imgPicture);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_picture)
        ImageView imgPicture ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
