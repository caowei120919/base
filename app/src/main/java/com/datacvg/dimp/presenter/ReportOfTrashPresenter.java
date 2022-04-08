package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportTrashListBean;
import com.datacvg.dimp.view.ReportOfTrashView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-07
 * @Description :
 */
public class ReportOfTrashPresenter extends BasePresenter<ReportOfTrashView>{
    MobileApi api ;

    @Inject
    public ReportOfTrashPresenter(MobileApi api) {
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

    /**
     *
     */
    public void clearReport() {
        Map params = new HashMap();
        params.put("resId","");
        params.put("type",Constants.REPORT_MINE);
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
                        if (((HttpException) e).code() == 500) {
                            BaseBean baseBean = null;
                            try {
                                baseBean = new Gson().fromJson(((HttpException) e).response().errorBody().string(), BaseBean.class);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            if (baseBean != null) {
                                ToastUtils.showLongToast(baseBean.getMessage());
                            }
                        }
                    }
                });
    }
}
