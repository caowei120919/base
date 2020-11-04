package com.datacvg.sempmobile.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.LanguageUtils;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.bean.ReportBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-03
 * @Description : 报告适配器
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private Context mContext ;
    private List<ReportBean> reportBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private String reportType = Constants.REPORT_MINE ;
    private OnReportClickListener listener ;

    public ReportAdapter(Context mContext, List<ReportBean> reportBeans, String reportType
            ,OnReportClickListener listener) {
        this.mContext = mContext;
        this.reportBeans = reportBeans;
        this.reportType = reportType ;
        this.listener = listener ;
        inflater = LayoutInflater.from(mContext);
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportBean bean = reportBeans.get(position) ;
        if(bean == null){
            return;
        }
        switch (reportType){
            case Constants.REPORT_MINE :
                if (bean.getModel_type().contains("folder")){
                    holder.imgPicture.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.report_folder_close));
                    holder.itemView.setOnClickListener(view -> {
                        listener.onFolderClick(bean);
                        PLog.e("文件夹被点击，打开文件夹");
                    });
                }else{
                    holder.imgPicture.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_report));
                    holder.itemView.setOnClickListener(view -> {
                        listener.onReportClick(bean);
                        PLog.e("报告被打开，跳转");
                    });
                }
                holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                        ? bean.getModel_clname() : bean.getModel_flname());
                break;

            case Constants.REPORT_SHARE :
                if (bean.getShare_showtype().contains("folder")){
                    holder.imgPicture.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.report_folder_close));
                    holder.itemView.setOnClickListener(view -> {
                        listener.onFolderClick(bean);
                        PLog.e("文件夹被点击，打开文件夹");
                    });
                }else{
                    holder.imgPicture.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.icon_report));
                    holder.itemView.setOnClickListener(view -> {
                        listener.onReportClick(bean);
                        PLog.e("报告被打开，跳转");
                    });
                }
                holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                        ? bean.getShare_clname() : bean.getShare_flname());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return reportBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_picture)
        ImageView imgPicture ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 报告点击监听
     */
    public interface OnReportClickListener{
        /**
         * 报告被点击
         * @param reportBean
         */
        void onReportClick(ReportBean reportBean);

        /**
         * 文件夹被点击
         * @param reportBean
         */
        void onFolderClick(ReportBean reportBean);
    }
}
