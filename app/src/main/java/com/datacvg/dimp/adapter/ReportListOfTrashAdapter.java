package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.ReportTrashBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-28
 * @Description :
 */
public class ReportListOfTrashAdapter extends RecyclerView.Adapter<ReportListOfTrashAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ReportTrashBean> reportBeans = new ArrayList<>() ;

    public ReportListOfTrashAdapter(Context mContext, List<ReportTrashBean> reportBeans) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.reportBeans = reportBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report_trash,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportTrashBean reportBean = reportBeans.get(position);
        holder.imgIcon.setImageBitmap(reportBean.getRes_type().endsWith("_folder")
                ? BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_folder)
                : BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_report));
        holder.tvReportName.setText(LanguageUtils.isZh(mContext)
                ? reportBean.getRes_clname() : reportBean.getRes_flname());
    }

    @Override
    public int getItemCount() {
        return reportBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_icon)
        ImageView imgIcon ;
        @BindView(R.id.tv_reportName)
        TextView tvReportName ;
        @BindView(R.id.rel_restore)
        RelativeLayout relRestore ;
        @BindView(R.id.rel_delete)
        RelativeLayout relDelete ;
        @BindView(R.id.img_functionDelete)
        ImageView imgFunctionDelete ;
        @BindView(R.id.tv_confirmDelete)
        TextView tvConfirmDelete ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
