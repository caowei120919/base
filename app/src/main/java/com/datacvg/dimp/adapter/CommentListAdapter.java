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
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {
    private Context mContext ;
    private List<CommentBean> commentBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private CommentPictureAdapter adapter ;
    private CommentChildAdapter childCommentAdapter ;
    private List<String> pictures = new ArrayList<>() ;
    private List<CommentBean> childComments = new ArrayList<>() ;

    public CommentListAdapter(Context mContext, List<CommentBean> commentBeans) {
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
        pictures.clear();
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
            initPictureAdapter(holder.recycleCommentPicture);
            for (CommentBean.AppImageNameBean appImageNameBean : commentBean.getAppImageNameList()){
                pictures.add(appImageNameBean.getImg_name()) ;
            }
            adapter.notifyDataSetChanged();
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
            holder.tvPackReply.setVisibility(commentBean.getChildComment().isEmpty() ? View.GONE
                    : View.VISIBLE);
            holder.tvPackReply.setSelected(commentBean.isHasSpread());
            childCommentAdapter = new CommentChildAdapter(mContext,childComments);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            holder.recycleChildComment.setLayoutManager(manager);
            holder.recycleChildComment.setAdapter(childCommentAdapter);
            holder.tvPackReply.setOnClickListener(view -> {
                if(commentBean.isHasSpread()){
                    childComments.clear();
                }else{
                    childComments.clear();
                    childComments.addAll(commentBean.getChildComment());
                }
                commentBean.setHasSpread(!commentBean.isHasSpread());
                childCommentAdapter.notifyDataSetChanged();
                holder.tvPackReply.setSelected(!holder.tvPackReply.isSelected());
                holder.tvPackReply.setText(holder.tvPackReply.isSelected() ? mContext.getResources().getString(R.string.pack_up) : mContext.getResources().getString(R.string.a_reply));
            });


            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }
    }

    /**
     * 初始化评论图片适配器
     * @param recycleCommentPicture
     */
    private void initPictureAdapter(RecyclerView recycleCommentPicture) {
        pictures.clear();
        adapter = new CommentPictureAdapter(mContext,pictures);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        recycleCommentPicture.setLayoutManager(gridLayoutManager);
        recycleCommentPicture.setAdapter(adapter);
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
