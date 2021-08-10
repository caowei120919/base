package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.KpiPermissionDataBean;
import com.datacvg.dimp.view.SnapShotView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-03
 * @Description :
 */
public class SnapShotPresenter extends BasePresenter<SnapShotView>{
    MobileApi api ;

    @Inject
    public SnapShotPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取快照对比自定义阈值名称
     */
    public void getPermissionName() {
        api.getPermissionName()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<KpiPermissionDataBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<KpiPermissionDataBean> baseBean) {
                        if(checkJsonCode(baseBean)){
                            getView().getKpiPermissionSuccess(baseBean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().getKpiPermissionFailed();
                    }
                });
    }
}
