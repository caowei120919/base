package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ChartBean;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.view.FeedBackView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-16
 * @Description :
 */
public class FeedBackPresenter extends BasePresenter<FeedBackView>{
    MobileApi api ;

    @Inject
    public FeedBackPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 提交评论信息
     * @param params
     */
    public void submitFeedBack(HashMap<String, String> params) {
        api.submitFeedBack(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(checkJsonCode(bean)){
                            ToastUtils.showLongToast(bean.getMessage());
                            getView().submitFeedBackSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
