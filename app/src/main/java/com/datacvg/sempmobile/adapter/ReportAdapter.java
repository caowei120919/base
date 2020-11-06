package com.datacvg.sempmobile.adapter;

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

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.DisplayUtils;
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
public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DISPLAY_GRID = 0 ;
    private final int DISPLAY_LIST = 1 ;
    private Context mContext ;
    private List<ReportBean> reportBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private String reportType = Constants.REPORT_MINE ;
    private String displayType = Constants.REPORT_GRID ;
    private OnReportClickListener listener ;


    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case DISPLAY_GRID :
                View viewGrid = inflater.inflate(R.layout.item_report_grid,parent,false);
                return new GridViewHolder(viewGrid);

            default:
                View viewList = inflater.inflate(R.layout.item_report_list,parent,false);
                return new ListViewHolder(viewList);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (displayType){
            case Constants.REPORT_GRID :

                return DISPLAY_GRID ;

            default:

                return DISPLAY_LIST ;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReportBean bean = reportBeans.get(position) ;
        if (holder instanceof GridViewHolder){
            holderGridViewHolder((GridViewHolder)holder,bean);
        }else{
            holderListViewHolder((ListViewHolder)holder,bean,position);
        }
    }

    private void holderListViewHolder(ListViewHolder holder, ReportBean bean, int position) {
        if(bean == null){
            return;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imgPicture.getLayoutParams();
        params.leftMargin = (bean.getLevel() - 1) * DisplayUtils.dp2px(20);
        switch (reportType){
            case Constants.REPORT_MINE :
                if (bean.getModel_type().contains("folder")){
                    if (bean.isOpen()){
                        holder.imgPicture.setImageBitmap(BitmapFactory
                                .decodeResource(mContext.getResources(),R.mipmap.report_folder_open));
                    }else{
                        holder.imgPicture.setImageBitmap(BitmapFactory
                                .decodeResource(mContext.getResources(),R.mipmap.report_folder_close));
                    }
                    holder.itemView.setOnClickListener(view -> {
                        listener.onListReportClick(position,bean);
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
                    if (bean.isOpen()){
                        holder.imgPicture.setImageBitmap(BitmapFactory
                                .decodeResource(mContext.getResources(),R.mipmap.report_folder_open));
                    }else{
                        holder.imgPicture.setImageBitmap(BitmapFactory
                                .decodeResource(mContext.getResources(),R.mipmap.report_folder_close));
                    }
                    holder.itemView.setOnClickListener(view -> {
                        listener.onListReportClick(position,bean);
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

    private void holderGridViewHolder(GridViewHolder holder, ReportBean bean) {
        if(bean == null){
            return;
        }
        switch (reportType){
            case Constants.REPORT_MINE :
                if (bean.getModel_type().contains("folder")){
                    holder.imgPicture.setImageBitmap(BitmapFactory
                            .decodeResource(mContext.getResources(),R.mipmap.report_folder_close));
                    holder.itemView.setOnClickListener(view -> {
                        listener.onGridFolderClick(bean);
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
                        listener.onGridFolderClick(bean);
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

    public class GridViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_picture)
        ImageView imgPicture ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_icon)
        ImageView imgPicture ;
        @BindView(R.id.tv_reportName)
        TextView tvTitle ;
        public ListViewHolder(@NonNull View itemView) {
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
        void onGridFolderClick(ReportBean reportBean);

        /**
         * 列表展示文件被点击
         * @param position
         * @param bean
         */
        void onListReportClick(int position,ReportBean bean);
    }
}
