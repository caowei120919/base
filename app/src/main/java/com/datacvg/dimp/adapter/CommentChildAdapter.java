package com.datacvg.dimp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-09
 * @Description :
 */
public class CommentChildAdapter extends RecyclerView.Adapter<CommentChildAdapter.ViewHolder> {
    private Context mContext ;
    private List<CommentBean> commentBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private CommentPictureAdapter adapter ;
    private List<String> pictures = new ArrayList<>() ;

    public CommentChildAdapter(Context mContext, List<CommentBean> commentBeans) {
        this.mContext = mContext;
        this.commentBeans = commentBeans;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        pictures = new ArrayList<>() ;
        if(commentBeans.get(position) != null){
            CommentBean commentBean =commentBeans.get(position) ;
            String comment = "" ;
            for (String arg : commentBean.getTexts()){
                if(arg.contains("@")){
                    comment = comment + "<font color = '#007AFF'>" + arg  + "</font>" ;
                }else{
                    comment = comment + arg ;
                }
            }
            adapter = new CommentPictureAdapter(mContext,pictures);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
            holder.recycleCommentPicture.setLayoutManager(gridLayoutManager);
            holder.recycleCommentPicture.setAdapter(adapter);
            for (CommentBean.AppImageNameBean appImageNameBean : commentBean.getAppImageNameList()){
                pictures.add(appImageNameBean.getImg_name()) ;
            }
            holder.tvComment.setText(Html.fromHtml(comment));
            holder.tvCommentName.setText(commentBean.getComment_username());
            holder.tvCommentTime.setText(commentBean.getUpdate_time());
            GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + Constants.HEAD_IMG_URL
                    + commentBean.getComment_user_id()
                    , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
            Glide.with(mContext).load(imgUrl).into(holder.imgHead);
            /**
             * 点击回复
             */
            holder.itemView.setOnClickListener(view -> {

            });
            holder.tvPackReply.setVisibility(View.GONE);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return commentBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_head)
        CircleImageView imgHead ;
        @BindView(R.id.tv_comment)
        TextView tvComment ;
        @BindView(R.id.tv_commentName)
        TextView tvCommentName ;
        @BindView(R.id.tv_commentTime)
        TextView tvCommentTime ;
        @BindView(R.id.recycle_commentPicture)
        RecyclerView recycleCommentPicture;
        @BindView(R.id.recycle_childComment)
        RecyclerView recycleChildComment ;
        @BindView(R.id.tv_packReply)
        TextView tvPackReply ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
