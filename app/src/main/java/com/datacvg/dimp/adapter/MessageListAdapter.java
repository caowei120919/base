package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.MessageBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-11
 * @Description : 消息列表
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int MESSAGE_OPEN = 0 ;
    private final int MESSAGE_CLOSE = 1 ;
    private Context mContext ;
    private List<MessageBean.ResultBean> messageBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private OnMessageClick click ;

    public MessageListAdapter(Context mContext, List<MessageBean.ResultBean> messageBeans
            ,OnMessageClick click) {
        this.mContext = mContext;
        this.messageBeans = messageBeans;
        this.click = click ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case MESSAGE_OPEN :
                View openMessage = inflater.inflate(R.layout.item_message_open,parent,false);
                return new OpenMessageViewHolder(openMessage);

            default:
                View closeMessage = inflater.inflate(R.layout.item_message_close,parent,false);
                return new CloseMessageViewHolder(closeMessage);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OpenMessageViewHolder){
            bindOpenMessage((OpenMessageViewHolder)holder,position);
        }else{
            bindCloseMessage((CloseMessageViewHolder)holder,position);
        }
    }

    /**
     * 绑定关闭样式
     * @param holder
     * @param position
     */
    private void bindCloseMessage(CloseMessageViewHolder holder, int position) {
        MessageBean.ResultBean bean = messageBeans.get(position);
        if(bean == null){
            return;
        }
        holder.tvSendName.setText(TextUtils.isEmpty(bean.getSend_user_name())
                ? "" : bean.getSend_user_name());
        holder.tvCreateTime.setText(TextUtils.isEmpty(bean.getMessage_time())
                ? "" : bean.getMessage_time());
        holder.tvTitle.setText(TextUtils.isEmpty(bean.getMessage_title())
                ? "" : bean.getMessage_title());
        if (bean.isRead_flag()){
            holder.imgReadFlag.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                    ,R.mipmap.icon_message_unread));
        }else{
            holder.imgReadFlag.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                    ,R.mipmap.icon_message_read));
        }
        holder.tvToDetail.setOnClickListener(view -> {
            click.toDetailClick(bean);
            PLog.e("查看详情");
        });
        holder.itemView.setOnClickListener(view -> {
            click.onReadClick(bean);
        });

    }

    /**
     * 绑定展开样式
     * @param holder
     * @param position
     */
    private void bindOpenMessage(OpenMessageViewHolder holder, int position) {
        MessageBean.ResultBean bean = messageBeans.get(position);
        if(bean == null){
            return;
        }
        String text = "" ;
        String helpUser = "" ;
        text = bean.getMessage_text() ;
        if(bean.getModule_id().equals(Constants.MSG_ACTION)){
            holder.tvToDetail.setVisibility(View.VISIBLE);
            text.replace("[task]","");
            if(text.contains("协助人")){
                String[] arr = text.split("协助人:");
                text = arr[0];
                helpUser = arr[1];
            }
            holder.tvAssistant.setVisibility(TextUtils.isEmpty(helpUser)
                    ? View.GONE : View.VISIBLE);
            holder.tvRecipientUser.setVisibility(TextUtils.isEmpty(helpUser)
                    ? View.GONE : View.VISIBLE);
            holder.tvRecipientUser.setText(helpUser);
        }else{
            holder.tvToDetail.setVisibility(View.GONE);
        }
        holder.tvSendName.setText(TextUtils.isEmpty(bean.getSend_user_name())
                ? "" : bean.getSend_user_name());
        holder.tvCreateTime.setText(TextUtils.isEmpty(bean.getMessage_time())
                ? "" : bean.getMessage_time());
        holder.tvTitle.setText(TextUtils.isEmpty(bean.getMessage_title())
                ? "" : bean.getMessage_title());
        if (bean.isRead_flag()){
            holder.imgReadFlag.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                    ,R.mipmap.icon_message_unread));
        }else{
            holder.imgReadFlag.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                    ,R.mipmap.icon_message_read));
        }
        holder.tvContent.setText(Html.fromHtml(TextUtils.isEmpty(text)
                ? "" : text));
        holder.tvRecipientUser.setText(TextUtils.isEmpty(bean.getReceive_user_name())
                ? "" : bean.getReceive_user_name());

        holder.tvToDetail.setOnClickListener(view -> {
            click.toDetailClick(bean);
            PLog.e("查看详情");
        });
        holder.itemView.setOnClickListener(view -> {
            click.onReadClick(bean);
        });
    }



    @Override
    public int getItemCount() {
        return messageBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messageBeans.get(position).isRead_flag()){
            return MESSAGE_OPEN ;
        }else{
            return MESSAGE_CLOSE ;
        }
    }

    /**
     * 消息展开
     */
    public class OpenMessageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_readFlag)
        ImageView imgReadFlag ;
        @BindView(R.id.tv_sendName)
        TextView tvSendName ;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;
        @BindView(R.id.tv_toDetail)
        TextView tvToDetail ;
        @BindView(R.id.tv_content)
        TextView tvContent ;
        @BindView(R.id.tv_recipientUser)
        TextView tvRecipientUser ;
        @BindView(R.id.tv_assistant)
        TextView tvAssistant ;
        @BindView(R.id.tv_assistantUser)
        TextView tvAssistantUser ;
        public OpenMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 消息闭合
     */
    public class CloseMessageViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_readFlag)
        ImageView imgReadFlag ;
        @BindView(R.id.tv_sendName)
        TextView tvSendName ;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;
        @BindView(R.id.tv_toDetail)
        TextView tvToDetail ;
        public CloseMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnMessageClick{
        /**
         * 查看详情
         * @param bean
         */
        void toDetailClick(MessageBean.ResultBean bean);

        /**
         * 修改已读
         * @param bean
         */
        void onReadClick(MessageBean.ResultBean bean);
    }
}
