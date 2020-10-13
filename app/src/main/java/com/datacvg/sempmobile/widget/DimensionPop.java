package com.datacvg.sempmobile.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.adapter.DimensionPopAdapter;
import com.datacvg.sempmobile.baseandroid.widget.CustomPopWindow;
import com.datacvg.sempmobile.bean.DimensionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 维度选择弹框
 */
public class DimensionPop {
    private Context mContext ;
    private CustomPopWindow popWindow ;
    private DimensionPopAdapter popAdapter ;
    private List<DimensionBean> dimensionBeans = new ArrayList<>() ;

    public DimensionPop(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.pop_dimension,null,false);
        RecyclerView recyclerDimension = view.findViewById(R.id.recycler_dimension);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        recyclerDimension.setLayoutManager(manager);
        popAdapter = new DimensionPopAdapter(mContext,dimensionBeans);
        recyclerDimension.setAdapter(popAdapter);
        popWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(view)
                .enableBackgroundDark(true)
                .create();
    }
}
