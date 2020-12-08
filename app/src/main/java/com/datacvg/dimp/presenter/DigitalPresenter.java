package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ChartBean;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.DimensionPositionListBean;
import com.datacvg.dimp.bean.OtherDimensionBean;
import com.datacvg.dimp.view.DigitalView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
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
     * @param mTimeValue
     * @param mOrgDimension
     * @param mFuDimension
     * @param mPDimension
     */
    public void getIndexPosition(String mTimeValue, String mOrgDimension, String mFuDimension, String mPDimension) {
        api.getIndexPosition(mTimeValue,mOrgDimension,mFuDimension,mPDimension)
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
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    public void getDimension(String timeVal) {
        api.getDimension(timeVal)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionListBean> bean) {
                        getView().getDimensionSuccess(bean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    /**
     *
     * @param type 传参类型(标记第二维度还是第三维度)
     * @param map 传参封装的map
     */
    public void getOtherDimension(String type,Map map) {
        api.getOtherDimension(map)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionListBean> bean) {
                        getView().getOtherDimensionSuccess(type,bean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
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
