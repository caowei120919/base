package com.datacvg.dimp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SelectChartTypeAdapter;
import com.datacvg.dimp.bean.ChartTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-08
 * @Description :
 */
public class DialogForChartType extends Dialog {

    private RecyclerView recyclerChartType ;
    private Context mContext ;
    private List<ChartTypeBean> objectBeans = new ArrayList<>() ;
    private SelectChartTypeAdapter selectChartTypeAdapter ;
    private SelectChartTypeAdapter.OnTypeClickListener clickListener ;

    public DialogForChartType(@NonNull Context context,List<ChartTypeBean> chartTypeBeans
            ,SelectChartTypeAdapter.OnTypeClickListener clickListener) {
        super(context);
        this.mContext = context ;
        this.objectBeans = chartTypeBeans ;
        this.clickListener = clickListener ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.item_pop_select_type);
        this.setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        recyclerChartType = findViewById(R.id.recycle_selectType);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerChartType.setLayoutManager(manager);
        recyclerChartType.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        selectChartTypeAdapter = new SelectChartTypeAdapter(mContext,objectBeans,clickListener);
        recyclerChartType.setAdapter(selectChartTypeAdapter);
    }
}
