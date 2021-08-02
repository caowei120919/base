package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ActionForIndexBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.view.ActionListView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ActionListPresenter extends BasePresenter<ActionListView>{
    MobileApi api ;

    @Inject
    public ActionListPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 根据指标获取关联的行动方案
     * @param indexId
     */
    public void getActionForIndexId(String indexId) {
        Map<String,String> params = new HashMap<>();
        params.put("type","3");
        params.put("index_id",indexId);
        api.getActionForIndexId(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ActionForIndexBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ActionForIndexBean> bean) {
                        if (checkJsonCode(bean)){
                            getView().getActionForIndexSuccess(bean.getData());
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
