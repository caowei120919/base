package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.config.UploadApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.IndexTreeListBean;
import com.datacvg.dimp.bean.ScreenReportListBean;
import com.datacvg.dimp.view.ScreenReportView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-03-24
 * @Description :
 */
public class ScreenReportPresenter extends BasePresenter<ScreenReportView>{
    MobileApi api ;
    UploadApi uploadApi ;

    @Inject
    public ScreenReportPresenter(MobileApi api, UploadApi uploadApi) {
        this.api = api;
        this.uploadApi = uploadApi;
    }

    public void getReportInScreen() {
        api.getReportInScreen().compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ScreenReportListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ScreenReportListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getReportInScreenSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    /**
     * 报告添加到大屏
     * @param params
     */
    public void addScreenReport(Map params) {
        api.addScreenReport(params).compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(checkJsonCode(bean)){
                            getView().addToScreenSuccess();
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
