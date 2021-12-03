package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.bean.ReportTrashBean;
import com.datacvg.dimp.bean.ReportTrashListBean;
import com.datacvg.dimp.view.ReportListOfTrashView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :
 */
public class ReportListOfTrashPresenter extends BasePresenter<ReportListOfTrashView>{
    MobileApi api ;

    @Inject
    public ReportListOfTrashPresenter(MobileApi api) {
        this.api = api;
    }

    public void queryReport(String reportType, String _t) {
        api.queryReport(reportType,_t)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ReportTrashListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ReportTrashListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().queryReportSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    public void clearReport() {
        Map params = new HashMap();
        params.put("resId","");
        params.put("type", Constants.REPORT_MINE);
        api.deleteReportOnTrash(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<Object>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(Object object) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e instanceof NullPointerException){
                        }
                        PLog.e("onError()" + e.getMessage());
                    }
                });

        Map shareParams = new HashMap();
        shareParams.put("resId","");
        shareParams.put("type",Constants.REPORT_SHARE);
        api.deleteReportOnTrash(shareParams)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<Object>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(Object object) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e instanceof NullPointerException){
                        }
                        PLog.e("onError()" + e.getMessage());
                    }
                });

        Map templateParams = new HashMap();
        templateParams.put("resId","");
        templateParams.put("type",Constants.REPORT_TEMPLATE);
        api.deleteReportOnTrash(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<Object>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(Object object) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e instanceof NullPointerException){
                            getView().clearSuccess();
                        }
                        PLog.e("onError()" + e.getMessage());
                    }
                });
    }

    public void deleteReportOnTrash(String reportType, String resId) {
        Map params = new HashMap();
        params.put("resId",resId);
        params.put("type",reportType);
        api.deleteReportOnTrash(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<Object>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(Object object) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e instanceof NullPointerException){
                            getView().deleteSuccess();
                        }
                        PLog.e("onError()" + e.getMessage());
                    }
                });
    }

    public void restoreOnTrash(String reportType, String resId) {
        Map params = new HashMap();
        params.put("resId",resId);
        params.put("type",reportType);
        api.restoreOnTrash(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<Object>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(Object object) {
                        getView().restoreSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("onError()" + e.getMessage());
                    }
                });
    }
}
