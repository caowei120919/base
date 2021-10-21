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

    public SearchReportAdapter(Context mContext,OnSearchReportClick click, List<ReportBean> reportBeans) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.click = click ;
        this.reportBeans = reportBeans;
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
        holder.tvReportName.setText(TextUtils.isEmpty(reportBean.getModel_clname())
                ? (TextUtils.isEmpty(reportBean.getShare_clname())
                ? reportBean.getShare_clname() : reportBean.getTemplate_clname())
                : reportBean.getModel_clname());
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
