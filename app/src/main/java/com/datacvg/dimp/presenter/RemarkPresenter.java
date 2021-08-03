package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.RemarkListBean;
import com.datacvg.dimp.bean.ReportParamsBean;
import com.datacvg.dimp.view.RemarkView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class RemarkPresenter extends BasePresenter<RemarkView>{
    MobileApi api ;

    @Inject
    public RemarkPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取对应备注信息
     * @param params
     */
    public void getRemark(Map<String, String> params) {
        api.getRemark(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<RemarkListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<RemarkListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getRemarkSuccess(bean.getResdata());
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
