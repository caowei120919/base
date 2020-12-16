package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.IndexTreeListBean;
import com.datacvg.dimp.view.IndexTreeView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
public class IndexTreePresenter extends BasePresenter<IndexTreeView>{
    MobileApi api ;

    @Inject
    public IndexTreePresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取指标树数据
     * @param map
     */
    public void getIndexTree(Map map) {
        api.getIndexTree(map)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<IndexTreeListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<IndexTreeListBean> bean) {
                        getView().getIndexTreeSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
