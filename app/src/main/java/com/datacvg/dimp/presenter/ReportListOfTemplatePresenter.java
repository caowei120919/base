package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.view.ReportListOfTemplateView;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfTemplatePresenter extends BasePresenter<ReportListOfTemplateView>{
    MobileApi api ;

    @Inject
    public ReportListOfTemplatePresenter(MobileApi api) {
        this.api = api;
    }

    /**
     *
     * @param reportTemplate
     * @param reportTemplateParentId
     * @param valueOf
     */
    public void getReportOfTemplate(String reportTemplate, String reportTemplateParentId, String valueOf) {
        api.getReport(reportTemplateParentId,reportTemplate,valueOf)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ReportListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ReportListBean> bean) {
                        if(RxObserver.checkJsonCode(bean)){
                            PLog.e(new Gson().toJson(bean.getData()));
                            getView().getReportOfTemplateSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    public void deleteReport(String resId, String reportType) {
        HashMap params = new HashMap();
        params.put("resId",resId);
        params.put("type",reportType);
        api.deleteReport(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        PLog.e("onNext()===============>" + bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof NullPointerException){
                            getView().deleteSuccess();
                        }
                    }
                });
    }

    public void downloadFile(String reportId, String type) {
        api.downReportFile(reportId,type)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<String>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(String bean) {
                        PLog.e("onNext()===============>" + bean);
                        getView().getReportSourceSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("onError()");
                    }
                });
    }
}
