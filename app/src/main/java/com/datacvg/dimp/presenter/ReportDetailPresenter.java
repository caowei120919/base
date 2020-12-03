package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ReportParamsBean;
import com.datacvg.dimp.view.ReportDetailView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-12
 * @Description :
 */
public class ReportDetailPresenter extends BasePresenter<ReportDetailView>{
    MobileApi api ;

    @Inject
    public ReportDetailPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取报表相关参数
     * @param reportId
     */
    public void getReportParameters(String reportId) {
        api.getReportParameters(reportId)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ReportParamsBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ReportParamsBean> bean) {
                        PLog.e(bean.getResdata().getParams() + "");
                        getView().getParamsSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
