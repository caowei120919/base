package com.datacvg.dimp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SelectChartDimensionAdapter;
import com.datacvg.dimp.adapter.SelectChartTypeAdapter;
import com.datacvg.dimp.bean.ChartTypeBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-08
 * @Description :
 */
public class DialogForDimension extends Dialog {

    private RecyclerView recyclerChartType ;
    private Context mContext ;
    private List<DimensionTypeBean> objectBeans = new ArrayList<>() ;
    private SelectChartDimensionAdapter selectChartTypeAdapter ;
    private SelectChartDimensionAdapter.OnTypeClickListener clickListener ;

    public DialogForDimension(@NonNull Context context, List<DimensionTypeBean> chartTypeBeans
            , SelectChartDimensionAdapter.OnTypeClickListener clickListener) {
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
        selectChartTypeAdapter = new SelectChartDimensionAdapter(mContext,objectBeans,clickListener);
        recyclerChartType.setAdapter(selectChartTypeAdapter);
    }
}
