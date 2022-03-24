package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.ScreenReportBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-03-24
 * @Description :
 */
public class ReportInScreenAdapter extends RecyclerView.Adapter<ReportInScreenAdapter.ViewHolder> {
    private Context mContext ;
    private List<ScreenReportBean> screenReportBeans = new ArrayList<>() ;
    private List<ScreenReportBean> screenReportChildBeans = new ArrayList<>() ;
    private LayoutInflater inflater ;
    private String TYPE_FOLDER = "FOLDER" ;

    public ReportInScreenAdapter(Context mContext, List<ScreenReportBean> screenReportBeans
            , List<ScreenReportBean> screenReportChildBeans) {
        this.mContext = mContext;
        this.screenReportBeans = screenReportBeans;
        this.screenReportChildBeans = screenReportChildBeans ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_screen_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setPadding(screenReportBeans.get(position).getLevel() * 50, 0, 0, 0);
        holder.itemView.setOnClickListener(v ->{
            if (TYPE_FOLDER.equals(screenReportBeans.get(position).getRes_showtype())) {
                if (screenReportBeans.get(position).getOpen()) {
                    screenReportBeans.get(position).setOpen(false);
                    removeAllChild(screenReportBeans.get(position).getChild());
                    holder.ivReportType.setImageResource(R.mipmap.filesimage);
                    holder.ivReportIndicator.setImageResource(R.mipmap.dash_pull_right);
                } else {
                    screenReportBeans.get(position).setOpen(true);
                    for (ScreenReportBean bean : screenReportChildBeans) {
                        if (screenReportBeans.get(position).getRes_id().equals(bean.getRes_parentid())) {
                            bean.setLevel(screenReportBeans.get(position).getLevel() + 1);
                            screenReportBeans.get(position).getChild().add(bean);
                            screenReportBeans.add(position + 1, bean);
                        }
                    }
                    holder.ivReportType.setImageResource(R.mipmap.filesmage_highlight);
                    holder.ivReportIndicator.setImageResource(R.mipmap.dash_pull);
                }
                notifyDataSetChanged();
            } else {
                if (screenReportBeans.get(position).getOpen()) {
                    screenReportBeans.get(position).setOpen(false);
                    holder.ivReportType.setImageResource(R.mipmap.report);
                    holder.ivReportCheck.setImageResource(R.mipmap.cx_unselect);
                } else {
                    screenReportBeans.get(position).setOpen(true);
                    holder.ivReportType.setImageResource(R.mipmap.report);
                    holder.ivReportCheck.setImageResource(R.mipmap.cx_select);
                }
            }
        });
        if (screenReportBeans.get(position).getOpen()) {
            if (TYPE_FOLDER.equals(screenReportBeans.get(position).getRes_showtype())) {
                holder.ivReportType.setImageResource(R.mipmap.filesmage_highlight);
                holder.ivReportIndicator.setImageResource(R.mipmap.dash_pull);
                holder.ivReportCheck.setVisibility(View.GONE);
                holder.ivReportIndicator.setVisibility(View.VISIBLE);
            } else {
                holder.ivReportType.setImageResource(R.mipmap.report);
                holder.ivReportCheck.setImageResource(R.mipmap.cx_select);
                holder.ivReportCheck.setVisibility(View.VISIBLE);
                holder.ivReportIndicator.setVisibility(View.GONE);
            }
        } else {
            if (TYPE_FOLDER.equals(screenReportBeans.get(position).getRes_showtype())) {
                holder.ivReportType.setImageResource(R.mipmap.filesimage);
                holder.ivReportIndicator.setImageResource(R.mipmap.dash_pull_right);
                holder.ivReportCheck.setVisibility(View.GONE);
                holder.ivReportIndicator.setVisibility(View.VISIBLE);
            } else {
                holder.ivReportType.setImageResource(R.mipmap.report);
                holder.ivReportCheck.setImageResource(R.mipmap.cx_unselect);
                holder.ivReportCheck.setVisibility(View.VISIBLE);
                holder.ivReportIndicator.setVisibility(View.GONE);
            }
        }
        holder.tvReportName.setText(screenReportBeans.get(position).getRes_clname());
    }

    private void removeAllChild(List<ScreenReportBean> childs) {
        if (childs.size() <= 0) {
            return ;
        }
        for (ScreenReportBean bean : childs) {
            screenReportBeans.remove(bean);
            removeAllChild(bean.getChild());
        }
    }

    @Override
    public int getItemCount() {
        return screenReportBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_report_type)
        ImageView ivReportType;
        @BindView(R.id.tv_report_name)
        TextView tvReportName;
        @BindView(R.id.iv_report_indicator)
        ImageView ivReportIndicator ;
        @BindView(R.id.iv_report_check)
        ImageView ivReportCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
