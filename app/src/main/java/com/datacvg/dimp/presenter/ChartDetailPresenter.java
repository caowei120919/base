package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.view.ChartDetailView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class ChartDetailPresenter extends BasePresenter<ChartDetailView>{
    MobileApi api ;

    @Inject
    public ChartDetailPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 查询图表数据
     * @param params
     */
    public void getChartData(HashMap params) {
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
}
