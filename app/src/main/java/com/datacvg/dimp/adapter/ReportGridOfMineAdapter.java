package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.datacvg.dimp.baseandroid.utils.PopupWindowUtil;
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
                holderMineViewHolder(holder,bean,position);
                break;

            case Constants.REPORT_SHARE :
                holderShareViewHolder(holder,bean,position);
                break;

            case Constants.REPORT_TEMPLATE :
                holderTemplateViewHolder(holder,bean,position);
                break;
        }
    }

    private void holderMineViewHolder(GridViewHolder holder, ReportBean bean,int position) {
        if(bean == null){
            return;
        }
        if (bean.getModel_type().contains("folder")){
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
                    .placeholder(R.mipmap.icon_folder)
                    .error(R.mipmap.icon_folder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imgPicture);
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
            showMenuPop(holder.imgMenu,bean,position);
        });
    }

    /**
     * 创建选择弹窗
     * @param bean
     * @param
     */
    private void showMenuPop(View view,ReportBean bean,int position) {
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_report_grid_dialog
                ,null,false);
        RelativeLayout relUploadThumb = containView.findViewById(R.id.rel_uploadThumb) ;
        RelativeLayout relAddScreen = containView.findViewById(R.id.rel_addScreen) ;
        RelativeLayout relDelete = containView.findViewById(R.id.rel_delete) ;
        RelativeLayout relDownLoad = containView.findViewById(R.id.rel_downLoad) ;
        relAddScreen.setVisibility(!bean.getFolder() ?  View.VISIBLE : View.GONE);
        relDownLoad.setVisibility((bean.isEditAble() && !bean.getFolder()) ? View.VISIBLE : View.GONE);
        relDelete.setVisibility(bean.isEditAble() ? View.VISIBLE : View.GONE );
        relUploadThumb.setOnClickListener(v -> {
            PLog.e("上传缩略图");
            listener.uploadThumb(bean);
        });
        relAddScreen.setOnClickListener(v -> {
            PLog.e("添加到大屏");
            listener.addToScreen(bean);
        });
        relDelete.setOnClickListener(v -> {
            PLog.e("删除报告");
            listener.deleteReport(bean);
        });
        relDownLoad.setOnClickListener(v -> {
            PLog.e("下载报告");
            listener.downloadReport(bean);
        });
        PopupWindow popupWindow = new PopupWindow(containView,
                (int) mContext.getResources().getDimension(R.dimen.W260), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(view, containView);
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, position%2 == 0 ? 200 : windowPos[0] - 100, windowPos[1]);
    }

    private void holderShareViewHolder(GridViewHolder holder, ReportBean bean,int position) {
        if(bean == null){
            return;
        }
        if (bean.getShare_showtype().contains("folder")){
            String imgUrl = String.format(Constants.BASE_URL + Constants.IMG_REPORT_URL,bean.getThumbnail_path());
            Glide.with(mContext).load(imgUrl)
                    .placeholder(R.mipmap.icon_folder)
                    .error(R.mipmap.icon_folder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imgPicture);
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
            showMenuPop(holder.imgMenu,bean,position);
        });
    }

    private void holderTemplateViewHolder(GridViewHolder holder, ReportBean bean,int position) {
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
            showMenuPop(holder.imgMenu,bean,position);
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
         * 上传缩略图
         * @param bean
         */
        void uploadThumb(ReportBean bean);

        /**
         * 添加到大屏
         * @param bean
         */
        void addToScreen(ReportBean bean);

        /**
         * 删除报告
         * @param bean
         */
        void deleteReport(ReportBean bean);

        /**
         * 下载报告
         * @param bean
         */
        void downloadReport(ReportBean bean);
    }
}
