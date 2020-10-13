package com.datacvg.sempmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.bean.ScreenBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-10
 * @Description :
 */
public class ScreenAdapter extends RecyclerView.Adapter<ScreenAdapter.ViewHolder> {
    private Context mContext ;
    private List<ScreenBean> screenBeans = new ArrayList<>();
    private LayoutInflater inflater ;

    public ScreenAdapter(Context mContext, List<ScreenBean> screenBeans) {
        this.mContext = mContext ;
        this.screenBeans = screenBeans ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_screen,parent,false);
        return new ScreenAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(screenBeans.get(position).getScreen_name());
        holder.tvName.setText(mContext.getResources().getString(R.string.screen) + (position + 1));
//        holder.tvDescribe.setText();

    }

    @Override
    public int getItemCount() {
        return screenBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rel_screen)
        RelativeLayout relScreen ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;
        @BindView(R.id.tv_describe)
        TextView tvDescribe ;
        @BindView(R.id.tv_name)
        TextView tvName ;
        @BindView(R.id.img_picture)
        ImageView imgPicture ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
