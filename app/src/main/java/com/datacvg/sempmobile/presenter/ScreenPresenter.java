package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ScreenListBean;
import com.datacvg.sempmobile.view.ScreenView;

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
