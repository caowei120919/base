package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.TableBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-02
 * @Description :
 */
public class ReportPopAdapter extends RecyclerView.Adapter<ReportPopAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<TableBean> tableBeans = new ArrayList<>();

    public ReportPopAdapter(Context mContext, List<TableBean> tableBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.tableBeans = tableBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pop_report,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TableBean tableBean = tableBeans.get(position);
        if(tableBean == null){
            return;
        }
        holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? tableBean.getRes_clname()
                : tableBean.getRes_flname());
    }

    @Override
    public int getItemCount() {
        return tableBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_reportName)
        TextView tvReportName ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
