package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.TableParamInfoListBean;
import com.datacvg.sempmobile.view.SelectTableParamView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description :
 */
public class SelectTableParamPresenter extends BasePresenter<SelectTableParamView>{
    MobileApi api ;

    @Inject
    public SelectTableParamPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取报表参数信息
     * @param res_id
     */
    public void getResParamInfo(String res_id) {
        api.getResParamInfo(res_id)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableParamInfoListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableParamInfoListBean> bean) {
                        getView().getParamInfoSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
