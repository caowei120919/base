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
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.widget.SwipeMenuLayout;
import com.datacvg.dimp.bean.ReportBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ViewHolder> {
    private Context mContext ;
    private LayoutInflater inflater ;
    private List<ReportBean> reportBeans = new ArrayList<>() ;
    private String folderType ;
    private OnReportListener listener ;

    public ReportListAdapter(Context mContext, String folderType, List<ReportBean> reportBeans,OnReportListener listener) {
        this.mContext = mContext;
        this.reportBeans = reportBeans;
        this.folderType = folderType ;
        this.listener = listener ;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportBean reportBean = reportBeans.get(position);
        switch (folderType){
            case Constants.REPORT_MINE :
                holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? reportBean.getModel_clname() : reportBean.getModel_flname());
                break;

            case Constants.REPORT_SHARE :
                holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? reportBean.getShare_clname() : reportBean.getShare_flname());
                break;

            case Constants.REPORT_TEMPLATE :
                holder.tvReportName.setText(LanguageUtils.isZh(mContext) ? reportBean.getTemplate_clname() : reportBean.getTemplate_flname());
                break;
        }
        holder.imgIcon.setImageBitmap(reportBean.getFolder()
                ? BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_folder)
                : BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.icon_report));
        holder.swipeMine.setSwipeEnable(reportBean.isEditAble() || !reportBean.getFolder());
        holder.relAddToScreen.setVisibility(!reportBean.getFolder() ?  View.VISIBLE : View.GONE);
        holder.relDownLoad.setVisibility((reportBean.isEditAble() && !reportBean.getFolder()) ? View.VISIBLE : View.GONE);
        holder.relDelete.setVisibility(reportBean.isEditAble() ? View.VISIBLE : View.GONE );
        holder.swipeMine.setCloseListener(new SwipeMenuLayout.OnCloseListener() {
            @Override
            public void closeSwipe() {
                holder.relAddToScreen.setVisibility(!reportBean.getFolder() ?  View.VISIBLE : View.GONE);
                holder.relDownLoad.setVisibility((reportBean.isEditAble() && !reportBean.getFolder()) ? View.VISIBLE : View.GONE);
                holder.relDelete.setVisibility(reportBean.isEditAble() ? View.VISIBLE : View.GONE);
                holder.imgFunctionDelete.setVisibility(View.VISIBLE);
                holder.tvConfirmDelete.setVisibility(View.GONE);
            }
        });
        holder.relDelete.setOnClickListener(v -> {
            holder.relAddToScreen.setVisibility(View.GONE);
            holder.relDownLoad.setVisibility(View.GONE);
            holder.imgFunctionDelete.setVisibility(View.GONE);
            holder.tvConfirmDelete.setVisibility(View.VISIBLE);
        });
        holder.tvConfirmDelete.setOnClickListener(v -> {
            listener.onReportDelete(reportBean);
        });
        holder.relAddToScreen.setOnClickListener(v -> {
            listener.onReportAddToScreen(reportBean);
        });
        holder.relDownLoad.setOnClickListener(v -> {
            listener.onReportDownload(reportBean);
        });
        holder.relItem.setOnClickListener(v -> {
            if(reportBean.getFolder()){
                listener.onListFolderClick(reportBean);
            }else{
                listener.onReportClick(reportBean);
            }
        });
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
        @BindView(R.id.rel_addToScreen)
        RelativeLayout relAddToScreen ;
        @BindView(R.id.img_functionAdd)
        ImageView imgFunctionAdd ;
        @BindView(R.id.rel_downLoad)
        RelativeLayout relDownLoad ;
        @BindView(R.id.img_functionDownload)
        ImageView imgFunctionDownload ;
        @BindView(R.id.rel_delete)
        RelativeLayout relDelete ;
        @BindView(R.id.img_functionDelete)
        ImageView imgFunctionDelete ;
        @BindView(R.id.swipe_mine)
        SwipeMenuLayout swipeMine ;
        @BindView(R.id.tv_confirmDelete)
        TextView tvConfirmDelete ;
        @BindView(R.id.rel_item)
        RelativeLayout relItem ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnReportListener{
        void onReportDelete(ReportBean reportBean);
        void onReportAddToScreen(ReportBean reportBean);
        void onReportDownload(ReportBean reportBean);
        void onListFolderClick(ReportBean reportBean);
        void onReportClick(ReportBean reportBean);
    }
}
