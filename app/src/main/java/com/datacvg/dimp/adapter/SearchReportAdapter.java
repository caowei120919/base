package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ReportBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :
 */
public class SearchReportAdapter extends RecyclerView.Adapter<SearchReportAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private OnSearchReportClick click ;
    private String reportType ;

    public SearchReportAdapter(Context mContext,OnSearchReportClick click,
                               List<ReportBean> reportBeans,String reportType) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.click = click ;
        this.reportBeans = reportBeans;
        this.reportType = reportType ;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportBean reportBean = reportBeans.get(position);
        if(reportBean.getFolder()){
            holder.imgIconType.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_folder));
        }else{
            holder.imgIconType.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_search_report));
        }
        PLog.e(reportType);
        switch (reportType){
            case Constants.REPORT_MINE :
                holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? reportBean.getModel_clname() : reportBean.getModel_flname());
                break;

            case Constants.REPORT_SHARE :
                holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? reportBean.getShare_clname() : reportBean.getShare_flname());
                break;

            case Constants.REPORT_TEMPLATE :
                holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? reportBean.getTemplate_clname() : reportBean.getTemplate_flname());
                break;
        }
        holder.itemView.setOnClickListener(v -> {
            click.onReportClick(reportBean);
        });
    }

    @Override
    public int getItemCount() {
        return reportBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_iconType)
        ImageView imgIconType ;
        @BindView(R.id.tv_reportName)
        TextView tvReportName ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnSearchReportClick{
        void onReportClick(ReportBean reportBean);
    }
}
