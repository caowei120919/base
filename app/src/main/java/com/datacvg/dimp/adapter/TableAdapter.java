package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.TableBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private Context mContext ;
    private List<TableBean> tableBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnItemClickListener listener ;

    public TableAdapter(Context mContext, List<TableBean> tableBeans ,OnItemClickListener listener) {
        this.mContext = mContext;
        this.tableBeans = tableBeans;
        this.listener = listener ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_table,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TableBean bean = tableBeans.get(position);
            if (bean == null){
                return;
            }
            holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                    ? bean.getRes_clname() : bean.getRes_flname());

            String imgUrl = String.format(Constants.BASE_URL + Constants.IMG_TAB_URL,bean.getRes_imgpath());
            Glide.with(mContext).load(imgUrl)
                    .placeholder(R.mipmap.screen_default)
                    .error(R.mipmap.screen_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imgPicture);

            holder.itemView.setOnClickListener(view -> {
                listener.onItemClick(bean);
            });
    }

    @Override
    public int getItemCount() {
        return tableBeans.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_picture)
        ImageView imgPicture ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        /**
         * 单项点击监听
         * @param tableBean
         */
        void onItemClick(TableBean tableBean);
    }
}
