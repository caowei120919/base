package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ReportParamsBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.view.ReportListView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ReportListPresenter extends BasePresenter<ReportListView> {
    MobileApi api ;

    @Inject
    public ReportListPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 根据指标id获取关联报表
     * @param indexId
     */
    public void getReportByIndexId(String indexId) {
        api.getReportByIndexId(indexId)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getReportsSuccess(bean.getResdata());
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
