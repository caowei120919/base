package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.view.SearchReportView;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-20
 * @Description :
 */
public class SearchReportPresenter extends BasePresenter<SearchReportView>{
    MobileApi api ;

    @Inject
    public SearchReportPresenter(MobileApi api) {
        this.api = api;
    }

    public void getReport(String type, String parentId, String _t) {
        api.getReport(parentId,type,_t)
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
                            getView().getReportSuccess(bean.getData());
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
