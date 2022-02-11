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
import com.datacvg.dimp.baseandroid.widget.SwipeMenuLayout;
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
        holder.swipeTrash.setCloseListener(new SwipeMenuLayout.OnCloseListener() {
            @Override
            public void closeSwipe() {
                holder.imgFunctionDelete.setVisibility(View.VISIBLE);
                holder.relRestore.setVisibility(View.VISIBLE);
                holder.tvConfirmDelete.setVisibility(View.GONE);
            }
        });
        holder.relDelete.setOnClickListener(v -> {
            holder.imgFunctionDelete.setVisibility(View.GONE);
            holder.relRestore.setVisibility(View.GONE);
            holder.tvConfirmDelete.setVisibility(View.VISIBLE);
        });
        holder.tvConfirmDelete.setOnClickListener(v -> {
            listener.onReportDelete(reportBean);
        });
        holder.relRestore.setOnClickListener(v -> {
            holder.relDelete.setVisibility(View.GONE);
            holder.imgFunctionDownload.setVisibility(View.GONE);
            holder.tvConfirmRestore.setVisibility(View.VISIBLE);
        });
        holder.tvConfirmRestore.setOnClickListener(v -> {
            listener.onReportRestore(reportBean);
        });
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
        @BindView(R.id.img_functionDownload)
        ImageView imgFunctionDownload ;
        @BindView(R.id.tv_confirmDelete)
        TextView tvConfirmDelete ;
        @BindView(R.id.tv_confirmRestore)
        TextView tvConfirmRestore ;
        @BindView(R.id.swipe_trash)
        SwipeMenuLayout swipeTrash ;

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

        void onReportDelete(ReportTrashBean reportBean);

        void onReportRestore(ReportTrashBean reportBean);
    }
}
