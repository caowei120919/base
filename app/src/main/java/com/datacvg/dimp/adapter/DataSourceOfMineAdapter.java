package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.TableParamInfoBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-12
 * @Description :
 */
public class DataSourceOfMineAdapter extends RecyclerView.Adapter<DataSourceOfMineAdapter.ViewHolder> {
    private Context mContext ;
    private List<TableParamInfoBean.DataSourceBean> dataSourceBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnItemClick itemClick ;

    public DataSourceOfMineAdapter(Context mContext
            , List<TableParamInfoBean.DataSourceBean> dataSourceBeans,OnItemClick itemClick) {
        this.mContext = mContext;
        this.dataSourceBeans = dataSourceBeans;
        inflater = LayoutInflater.from(mContext);
        this.itemClick = itemClick ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_datasource_mine,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TableParamInfoBean.DataSourceBean dataSourceBean = dataSourceBeans.get(position);
        holder.tvIndexName.setText(LanguageUtils.isZh(mContext) ? dataSourceBean.getD_res_clname()
                : dataSourceBean.getD_res_flname());
        holder.itemView.setOnClickListener(view -> {
            itemClick.onMineClick(dataSourceBean);
        });
    }

    @Override
    public int getItemCount() {
        return dataSourceBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_indexName)
        TextView tvIndexName ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClick{
        void onMineClick(TableParamInfoBean.DataSourceBean dataSourceBean);
    }
}
