package com.datacvg.dimp.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.dimp.baseandroid.greendao.controller.DbModuleInfoController;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-28
 * @Description :
 */
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    private Context mContext ;
    private List<ModuleInfo> moduleInfos = new ArrayList<>();
    private LayoutInflater inflater ;
    /**
     * 个人中心默认为选中且不展示的
     */
    private final int MAX_SELECTED = 5 ;

    public ModuleAdapter(Context mContext, List<ModuleInfo> moduleInfos) {
        this.mContext = mContext;
        this.moduleInfos = moduleInfos;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_module,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvModuleName.setText(moduleInfos.get(position).getModule_name());
        holder.imgModulePicture.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                ,moduleInfos.get(position).getModule_normal_res()));
        holder.cbSelectModule.setChecked(moduleInfos.get(position).getModule_checked());
        holder.cbSelectModule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && DbModuleInfoController.getInstance(mContext)
                        .getSelectedModuleList().size() >= MAX_SELECTED){
                    ToastUtils.showShortToast(mContext.getResources()
                            .getString(R.string.select_up_to_four_modules));
                    compoundButton.setChecked(false);
                }else{
                    holder.cbSelectModule.setClickable(false);
                    ModuleInfo moduleInfo = moduleInfos.get(position);
                    moduleInfo.setModule_checked(b);
                    DbModuleInfoController.getInstance(mContext).updateModuleInfo(moduleInfo);
                    holder.cbSelectModule.setClickable(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_moduleName)
        TextView tvModuleName ;
        @BindView(R.id.img_modulePicture)
        ImageView imgModulePicture ;
        @BindView(R.id.cb_selectModule)
        CheckBox cbSelectModule ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
