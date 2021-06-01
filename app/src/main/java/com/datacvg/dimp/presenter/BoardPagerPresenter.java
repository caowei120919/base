package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.view.BoardPagerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class BoardPagerPresenter extends BasePresenter<BoardPagerView>{
    MobileApi api ;

    @Inject
    public BoardPagerPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 根据页面获取指标信息
     * @param params
     */
    public void getPosition(Map params) {
        api.getIndexPosition(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionPositionBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionPositionBean> bean) {
                        getView().getIndexPositionSuccess(bean.getData().getIndexPosition());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 查询图表信息
     * @param params
     */
    public void getEChart(HashMap params) {
        api.getEChart(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<EChartListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<EChartListBean> bean) {
                        if (bean.getData().getIndexChart().size() > 0){
                            getView().getChartSuccess(bean.getData().getIndexChart().get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getDimension(Map params) {
        api.getDimension(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionListBean> bean) {
                        getView().getDimensionSuccess(bean.getData().getSelectDimension());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getOtherDimension(Map params,String tag) {
        api.getOtherDimension(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionListBean> bean) {
                        getView().getOtherDimensionSuccess(bean.getData()
                                .getSelectOtherDimension(),tag);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
