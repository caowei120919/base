package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.SearchListReportBean;
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

    /**
     * 根据名称搜索报告信息
     * @param searchText
     */
    public void searchReportForName(String searchText) {
        api.searchReport(searchText)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<SearchListReportBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("onComplete()");
                    }

                    @Override
                    public void onNext(BaseBean<SearchListReportBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getSearchReportListSuccess(bean.getData());
                        }
                        PLog.e(new Gson().toJson(bean));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("onError()" + e.getMessage());
                    }
                });
    }
}
