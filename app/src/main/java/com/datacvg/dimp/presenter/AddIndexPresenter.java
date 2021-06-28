package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.AllIndexBean;
import com.datacvg.dimp.bean.RecommendIndexBean;
import com.datacvg.dimp.view.AddIndexView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public class AddIndexPresenter extends BasePresenter<AddIndexView>{
    MobileApi api ;

    @Inject
    public AddIndexPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 根据page页获取
     * @param pageNo
     */
    public void getRecommendIndexInfo(String pageNo) {
        api.getRecommendIndexInfo(pageNo).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<RecommendIndexBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<RecommendIndexBean> bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            getView().getRecommendIndexSuccess(bean.getData().getIndexChartRelation());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 获取所有指标信息
     */
    public void getAllIndexInfo() {
        api.getAllIndexInfo().compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<AllIndexBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<AllIndexBean> bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            getView().getAllIndexSuccess(bean.getData().getAddAbleIndexInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void changeChart(Map params) {
        api.changeChart(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            getView().saveSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
