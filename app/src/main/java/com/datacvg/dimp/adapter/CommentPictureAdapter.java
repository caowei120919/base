package com.datacvg.dimp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datacvg.dimp.R;
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
        Glide.with(mContext).load(imageList.get(position))
                .error(R.mipmap.img_default).into(holder.imgPicture);
        holder.imgDelete.setOnClickListener(view -> {
            EventBus.getDefault().post(new DeleteCommentEvent(position));
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_picture)
        ImageView imgPicture ;
        @BindView(R.id.img_delete)
        ImageView imgDelete ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
