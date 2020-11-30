package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ScreenListBean;
import com.datacvg.sempmobile.bean.TableInfoBean;
import com.datacvg.sempmobile.bean.TableParamInfoBean;
import com.datacvg.sempmobile.bean.TableParamInfoListBean;
import com.datacvg.sempmobile.view.TableDetailView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-26
 * @Description :
 */
public class TableDetailPresenter extends BasePresenter<TableDetailView>{
    MobileApi api ;

    @Inject
    public TableDetailPresenter(MobileApi api) {
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

    /**
     * 获取报表url
     * @param paramArr
     * @param resId
     */
    public void getTableUrl(String paramArr, String resId) {
        Map<String,String> params = new HashMap<>();
        params.put("paramStr",paramArr);
        params.put("res_id",resId);
        api.getTableUrl(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableInfoBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableInfoBean> bean) {
                        getView().getTableInfoSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
