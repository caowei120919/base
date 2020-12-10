package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ChartBean;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.view.IndexDetailView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
public class IndexDetailPresenter extends BasePresenter<IndexDetailView>{
    private MobileApi api ;

    @Inject
    public IndexDetailPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取图表信息数据
     * @param map
     */
    public void getChart(Map map) {
        api.getCharts(map)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ChartListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ChartListBean> bean) {
                        PLog.e(new Gson().toJson(bean));
                        getView().getChartSuccess(bean.getResdata());
                        List<String> stringName = new ArrayList<>();
                        List<Float> floatValue = new ArrayList<>();
                        for (ChartBean chartBean : bean.getResdata()) {
                            if(chartBean.getChart_type().equals("pie_chart")){
                                List<Object> datas = chartBean.getOption().getSeries().get(0).getData();
                                for (int i = 0 ; i < datas.size() ; i++){
                                    stringName.add((String) ((LinkedTreeMap)datas.get(i)).get("name"));
                                    floatValue.add(Float.valueOf((String) ((LinkedTreeMap)datas.get(i)).get("value")));
                                    PLog.e(((LinkedTreeMap)datas.get(i)).get("name") + "----" + ((LinkedTreeMap)datas.get(i)).get("value"));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
