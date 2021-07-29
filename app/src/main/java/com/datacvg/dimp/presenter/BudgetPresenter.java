package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.DigitalPageBean;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.bean.PageItemListBean;
import com.datacvg.dimp.bean.PerformanceBean;
import com.datacvg.dimp.view.BudgetView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public class BudgetPresenter extends BasePresenter<BudgetView>{
    MobileApi api ;

    @Inject
    public BudgetPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取绩效数据
     * @param params
     */
    public void getBudget(HashMap params) {
        api.getBudget(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<PerformanceBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<PerformanceBean> budgetBean) {
                        if(checkJsonCode(budgetBean)){
                            getView().getBudgetSuccess(budgetBean.getData().getIndexPositionForBudget());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getEChart(HashMap params) {
        api.getEChart(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<EChartListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<EChartListBean> bean) {
                        if(checkJsonCode(bean)){
                            if (bean.getData().getIndexChart().size() > 0){
                                getView().getChartSuccess(bean.getData().getIndexChart().get(0));
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
