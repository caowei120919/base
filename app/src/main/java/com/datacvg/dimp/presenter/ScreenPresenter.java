package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ScreenListBean;
import com.datacvg.dimp.view.ScreenView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class ScreenPresenter extends BasePresenter<ScreenView>{
    MobileApi api ;

    @Inject
    public ScreenPresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 根据排序规则查询大屏列表
     * @param screenType 排序规则
     */
    public void getScreenList(String screenType) {
        api.getScreenList(screenType)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<ScreenListBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(ScreenListBean bean) {
                        getView().getScreenSuccess(bean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
