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
public class ChooseScreenAdapter extends RecyclerView.Adapter<ChooseScreenAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ScreenBean> screenBeans = new ArrayList<>() ;
    private OnScreenChooseClick click ;

    public ChooseScreenAdapter(Context mContext,OnScreenChooseClick click,
                               List<ScreenBean> screenBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.click = click ;
        this.screenBeans = screenBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_choose_screen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScreenBean screenBean = screenBeans.get(position);
        holder.tvScreen.setText(screenBean.getScreen_name());
        holder.itemView.setOnClickListener(v -> {
            click.onChooseClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return screenBeans.size();
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
    public interface OnScreenChooseClick{
        void onChooseClick(int position);
    }
}
