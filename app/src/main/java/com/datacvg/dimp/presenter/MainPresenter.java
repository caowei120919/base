package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.ModuleListBean;
import com.datacvg.dimp.view.MainView;
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

    /**
     * 获取用户拥有权限的模块
     */
    public void getPermissionModule() {
        mobileApi.getPermissionModule()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ModuleListBean>>(){
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(BaseBean<ModuleListBean> stringBaseBean) {
                        getView().getModuleSuccess(stringBaseBean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
