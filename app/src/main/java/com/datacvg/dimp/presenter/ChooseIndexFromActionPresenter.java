package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.view.ChooseIndexFromActionView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-17
 * @Description :
 */
public class ChooseIndexFromActionPresenter extends BasePresenter<ChooseIndexFromActionView>{
    MobileApi api ;

    @Inject
    public ChooseIndexFromActionPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取行动方案指标数据
     */
    public void getActionPlanIndex() {
        api.getActionPlanIndex()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ActionPlanIndexListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ActionPlanIndexListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getIndexSuccess(bean.getResdata());
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
