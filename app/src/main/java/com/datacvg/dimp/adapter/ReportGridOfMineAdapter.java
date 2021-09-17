package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
 * @Time : 2020-11-03
 * @Description : 报告适配器
 */
public class ReportGridOfMineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext ;
    private List<ReportBean> reportBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnReportClickListener listener ;


    public ReportGridOfMineAdapter(Context mContext, List<ReportBean> reportBeans
            , OnReportClickListener listener) {
        this.mContext = mContext;
        this.reportBeans = reportBeans;
        this.listener = listener ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewGrid = inflater.inflate(R.layout.item_report_grid,parent,false);
        return new GridViewHolder(viewGrid);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReportBean bean = reportBeans.get(position) ;
        holderGridViewHolder((GridViewHolder)holder,bean);
    }

    private void holderGridViewHolder(GridViewHolder holder, ReportBean bean) {
        if(bean == null){
            return;
        }
        if (bean.getModel_type().contains("folder")){
            holder.imgPicture.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_folder));
            holder.itemView.setOnClickListener(view -> {
                listener.onGridFolderClick(bean);
                PLog.e("文件夹被点击，打开文件夹");
            });
        }else{
            String imgUrl = String.format(Constants.BASE_URL + Constants.IMG_REPORT_URL,bean.getThumbnail_path());
            Glide.with(mContext).load(imgUrl)
                    .placeholder(R.mipmap.icon_report)
                    .error(R.mipmap.icon_report)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imgPicture);
            holder.itemView.setOnClickListener(view -> {
                listener.onReportClick(bean);
                PLog.e("报告被打开，跳转" + bean.getModel_id());
            });
        }
        holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                ? bean.getModel_clname() : bean.getModel_flname());
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
    }
}
