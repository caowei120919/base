package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.DimensionForTimeBean;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.IndexChartInfoBean;
import com.datacvg.dimp.view.AddIndexPageView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-01
 * @Description :
 */
public class AddIndexPagePresenter extends BasePresenter<AddIndexPageView>{
    MobileApi api ;

    @Inject
    public AddIndexPagePresenter(MobileApi api) {
        this.api = api;
    }

    public void getIndexPageDimension(String timeVal) {
        api.getIndexPageDimension(timeVal).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DimensionForTimeBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<DimensionForTimeBean> bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            getView().getIndexSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void addPageRequest(HashMap params) {
        api.addPageRequest(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            getView().addPageSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 根据维度获取指标
     * @param params
     */
    public void getIndexForDimension(Map params) {
        api.getIndexForDimension(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<IndexChartInfoBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<IndexChartInfoBean> bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            getView().getIndexForDimensionSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
