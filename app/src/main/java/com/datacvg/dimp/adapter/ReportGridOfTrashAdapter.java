package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.PopupWindowUtil;
import com.datacvg.dimp.bean.ReportBean;
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
public class ReportGridOfTrashAdapter extends RecyclerView.Adapter<ReportGridOfTrashAdapter.ViewHolder> {

    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ReportTrashBean> reportTrashBeans = new ArrayList<>() ;
    private OnReportTrashClickListener listener ;
    private boolean isEdit = false ;

    public ReportGridOfTrashAdapter(Context mContext,OnReportTrashClickListener listener
            , List<ReportTrashBean> reportTrashBeans) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.listener = listener ;
        this.reportTrashBeans = reportTrashBeans;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportTrashBean reportTrashBean = reportTrashBeans.get(position);
        if(reportTrashBean == null){
            return;
        }
        if (reportTrashBean.getRes_type().endsWith("_folder")){
            holder.imgPicture.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),R.mipmap.icon_folder));
        }else{
            String imgUrl = String.format(Constants.BASE_URL + Constants.IMG_REPORT_URL,reportTrashBean.getThumbnail_path());
            Glide.with(mContext).load(imgUrl)
                    .placeholder(R.mipmap.icon_report)
                    .error(R.mipmap.icon_report)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imgPicture);
        }
        holder.tvTitle.setText(LanguageUtils.isZh(mContext)
                ? reportTrashBean.getRes_clname() : reportTrashBean.getRes_flname());
        if(isEdit){
            holder.imgMenu.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                    ,reportTrashBean.getChecked() ? R.mipmap.icon_trash_check : R.mipmap.icon_trash_uncheck));
            holder.imgMenu.setOnClickListener(v -> {
                reportTrashBean.setChecked(!reportTrashBean.getChecked());
                listener.checkReport(reportTrashBean);
                notifyDataSetChanged();
            });
        }else{
            holder.imgMenu.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                    , R.mipmap.icon_report_menu));
            holder.imgMenu.setOnClickListener(v -> {
                PLog.e("菜单点击处理");
                showMenuPop(holder.imgMenu,reportTrashBean,position);
            });
        }
    }

    /**
     * 创建选择弹窗
     * @param bean
     * @param
     */
    private void showMenuPop(View view,ReportTrashBean bean,int position) {
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_report_trash_dialog
                ,null,false);
        RelativeLayout relDelete = containView.findViewById(R.id.rel_delete) ;
        RelativeLayout relRestore = containView.findViewById(R.id.rel_restore) ;
        PopupWindow popupWindow = new PopupWindow(containView,
                (int) mContext.getResources().getDimension(R.dimen.W260), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        relDelete.setOnClickListener(v -> {
            listener.deleteReport(bean);
            if(popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        });
        relRestore.setOnClickListener(v -> {
            listener.restoreReport(bean);
            if(popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        });
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(view, containView);
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, position%2 == 0 ? 200 : windowPos[0] - 100, windowPos[1]);
    }

    @Override
    public int getItemCount() {
        return reportTrashBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_picture)
        ImageView imgPicture ;
        @BindView(R.id.img_menu)
        ImageView imgMenu ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;

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

        void deleteReport(ReportTrashBean bean);

        void restoreReport(ReportTrashBean bean);
    }
}
