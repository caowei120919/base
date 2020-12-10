package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-09
 * @Description :
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {
    private Context mContext ;
    private List<CommentBean> commentBeans = new ArrayList<>();
    private LayoutInflater inflater ;

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
        if(commentBeans.get(position) != null){
            CommentBean commentBean =commentBeans.get(position) ;
            String comment = "" ;
            for (String arg : commentBean.getTexts()){
                comment = comment + arg ;
            }
            holder.tvComment.setText(comment);
            holder.tvCommentName.setText(commentBean.getComment_username());
            holder.tvCommentTime.setText(commentBean.getUpdate_time());
            /**
             * 点击回复
             */
            holder.itemView.setOnClickListener(view -> {

            });


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

    public void setCommentBeans(List<CommentBean> mCommentBeans) {
        this.commentBeans.clear();
        this.commentBeans.addAll(mCommentBeans);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_comment)
        TextView tvComment ;
        @BindView(R.id.tv_commentName)
        TextView tvCommentName ;
        @BindView(R.id.tv_commentTime)
        TextView tvCommentTime ;
        @BindView(R.id.recycle_commentPicture)
        RecyclerView recycleCommentPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
