package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.ScreenBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-22
 * @Description :
 */
public class ChooseScreenSizeAdapter extends RecyclerView.Adapter<ChooseScreenSizeAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<String> screenSizes = new ArrayList<>() ;
    private OnScreenSizeChooseClick click ;

    public ChooseScreenSizeAdapter(Context mContext, OnScreenSizeChooseClick click,
                                   List<String> screenSizes) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.click = click ;
        this.screenSizes = screenSizes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_choose_screen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvScreen.setText(screenSizes.get(position));
        holder.itemView.setOnClickListener(v -> {
            click.onSizeChooseClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return screenSizes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_screen)
        TextView tvScreen ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 大屏选择点击监听
     */
    public interface OnScreenSizeChooseClick{
        void onSizeChooseClick(int position);
    }
}
