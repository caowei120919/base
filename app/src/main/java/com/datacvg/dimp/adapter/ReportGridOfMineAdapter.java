package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ReportBean;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-03
 * @Description : 报告适配器
 */
public class ReportGridOfMineAdapter extends RecyclerView.Adapter<ReportGridOfMineAdapter.GridViewHolder> {
    private Context mContext ;
    private List<ReportBean> reportBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnReportClickListener listener ;
    private String reportType ;


    public ReportGridOfMineAdapter(Context mContext,String reportType ,List<ReportBean> reportBeans
            , OnReportClickListener listener) {
        this.mContext = mContext;
        this.reportBeans = reportBeans;
        this.listener = listener ;
        this.reportType = reportType ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewGrid = inflater.inflate(R.layout.item_report_grid,parent,false);
        return new GridViewHolder(viewGrid);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        ReportBean bean = reportBeans.get(position) ;
        switch (reportType){
            case Constants.REPORT_MINE :
                holderMineViewHolder(holder,bean);
                break;

            case Constants.REPORT_SHARE :
                holderShareViewHolder(holder,bean);
                break;

            case Constants.REPORT_TEMPLATE :
                holderTemplateViewHolder(holder,bean);
                break;
        }
    }

    private void holderMineViewHolder(GridViewHolder holder, ReportBean bean) {
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
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (resource instanceof GifDrawable) {
                                //加载一次
                                ((GifDrawable)resource).setLoopCount(GifDrawable.LOOP_FOREVER);
                            }
                            return false;
                        }
                    })
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
        holder.imgMenu.setVisibility((bean.isEditAble() || !bean.getFolder()) ? View.VISIBLE : View.GONE);
        holder.imgMenu.setOnClickListener(v -> {
            PLog.e("菜单点击处理");
            listener.onMenuClick(bean);
        });
    }

    private void holderShareViewHolder(GridViewHolder holder, ReportBean bean) {
        if(bean == null){
            return;
        }
        if (bean.getShare_showtype().contains("folder")){
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
                PLog.e("报告被打开，跳转" + bean.getShare_id());
            });
        }
        holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                ? bean.getShare_clname() : bean.getShare_flname());
        holder.imgMenu.setVisibility((bean.isEditAble() || !bean.getFolder()) ? View.VISIBLE : View.GONE);
        holder.imgMenu.setOnClickListener(v -> {
            PLog.e("菜单点击处理");
            listener.onMenuClick(bean);
        });
    }

    private void holderTemplateViewHolder(GridViewHolder holder, ReportBean bean) {
        if(bean == null){
            return;
        }
        if (bean.getTemplate_showtype().contains("folder")){
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
                PLog.e("报告被打开，跳转" + bean.getTemplate_id());
            });
        }
        holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                ? bean.getTemplate_clname() : bean.getTemplate_flname());
        holder.imgMenu.setVisibility((bean.isEditAble() || !bean.getFolder())? View.VISIBLE : View.GONE);
        holder.imgMenu.setOnClickListener(v -> {
            PLog.e("菜单点击处理");
            listener.onMenuClick(bean);
        });
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
        @BindView(R.id.img_menu)
        ImageView imgMenu ;
        public GridViewHolder(@NonNull View itemView) {
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
         * 菜单栏点击监听
         * @param reportBean
         */
        void onMenuClick(ReportBean reportBean);
    }
}
