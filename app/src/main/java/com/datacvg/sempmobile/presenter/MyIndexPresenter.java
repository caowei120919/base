package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.DimensionPositionListBean;
import com.datacvg.sempmobile.bean.IndexBean;
import com.datacvg.sempmobile.view.MyIndexView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-30
 * @Description :
 */
public class MyIndexPresenter extends BasePresenter<MyIndexView>{
    MobileApi api ;
    @Inject
    public MyIndexPresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 获取指标信息
     */
    public void getIndex() {
        api.getIndex()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<IndexBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<IndexBean> bean) {
                        getView().getIndexSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 根据id，修改我的指标排序与指标
     * @param indexIds
     */
    public void changeSelectedIndex(String indexIds) {
        api.changeSelectedIndex(indexIds)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<String>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<String> bean) {
                        getView().changeIndexSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().changeIndexFail();
                    }
                });
    }
}
