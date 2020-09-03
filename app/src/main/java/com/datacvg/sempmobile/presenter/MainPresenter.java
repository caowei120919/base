package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ModuleListBean;
import com.datacvg.sempmobile.view.MainView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :
 */
public class MainPresenter extends BasePresenter<MainView>{
    MobileApi mobileApi ;

    @Inject
    public MainPresenter(MobileApi mobileApi ){
        this.mobileApi = mobileApi;
    }

    public void getPermissionModule() {
        mobileApi.getPermissionModule()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ModuleListBean>>(){
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(BaseBean<ModuleListBean> stringBaseBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
