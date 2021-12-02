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
    private boolean isEdit = false ;
    private OnReportTrashClickListener listener ;

    public ReportListOfTrashAdapter(Context mContext, OnReportTrashClickListener listener ,List<ReportTrashBean> reportBeans,boolean isEdit) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.listener = listener ;
        this.reportBeans = reportBeans;
        this.isEdit = isEdit ;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
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
        holder.imgSelected.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        if(isEdit){
            holder.imgSelected.setSelected(reportBean.getChecked());
            holder.imgSelected.setOnClickListener(v -> {
                holder.imgSelected.setSelected(!holder.imgSelected.isSelected());
                reportBean.setChecked(holder.imgSelected.isSelected());
                listener.checkReport(reportBean);
            });
        }
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
        @BindView(R.id.img_selected)
        ImageView imgSelected ;
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

    /**
     * 回收站操作监听
     */
    public interface OnReportTrashClickListener{
        void checkReport(ReportTrashBean bean);
    }
}
