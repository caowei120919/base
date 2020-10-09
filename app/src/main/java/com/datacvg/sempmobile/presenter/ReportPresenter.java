package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.FisApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.view.ReportView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class ReportPresenter extends BasePresenter<ReportView>{
    private FisApi api ;

    @Inject
    public ReportPresenter(FisApi api) {
        this.api = api ;
    }

    public void getReport(String reportType, String _t) {
        api.getReport(reportType,_t)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<String>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<String> bean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
