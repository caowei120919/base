package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.view.SelectFilterView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-19
 * @Description :
 */
public class SelectFilterPresenter extends BasePresenter<SelectFilterView>{
    MobileApi api ;

    @Inject
    public SelectFilterPresenter(MobileApi api) {
        this.api = api;
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
