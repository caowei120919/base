package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ChartBean;
import com.datacvg.sempmobile.bean.ChartListBean;
import com.datacvg.sempmobile.bean.DimensionListBean;
import com.datacvg.sempmobile.bean.DimensionPositionListBean;
import com.datacvg.sempmobile.bean.OtherDimensionBean;
import com.datacvg.sempmobile.view.DigitalView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class DigitalPresenter extends BasePresenter<DigitalView>{
    MobileApi api ;

    @Inject
    public DigitalPresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 获取指标列表
     */
    public void getIndexPosition() {
        api.getIndexPosition()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionPositionListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionPositionListBean> bean) {
                        getView().getDimensionPositionSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getDimension() {
        api.getDimension()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionListBean> bean) {
                        getView().getDimensionSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getOtherDimension() {
        api.getOtherDimension()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<OtherDimensionBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<OtherDimensionBean> bean) {
                        getView().getOtherDimensionSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getCharts(Map map) {
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
                        List<String> stringName = new ArrayList<>();
                        List<Float> floatValue = new ArrayList<>();
                        for (ChartBean chartBean : bean.getResdata()) {
                            if(chartBean.getChart_type().equals("pie_chart")){
                                List<Object> datas = chartBean.getOption().getSeries().get(0).getData();
                                for (int i = 0 ; i < datas.size() ; i++){
                                    stringName.add((String) ((LinkedTreeMap)datas.get(i)).get("name"));
                                    floatValue.add(Float.valueOf((String) ((LinkedTreeMap)datas.get(i)).get("value")));
                                    PLog.e((String) ((LinkedTreeMap)datas.get(i)).get("name") + "----" + ((String) ((LinkedTreeMap)datas.get(i)).get("value")));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
