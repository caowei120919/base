package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ActionPlanListBean;
import com.datacvg.sempmobile.bean.UserLoginBean;
import com.datacvg.sempmobile.view.ActionView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class ActionPresenter extends BasePresenter<ActionView>{

    MobileApi api ;
    @Inject
    public ActionPresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 获取行动方案列表
     * @param params
     */
    public void getActionList(Map params) {
        api.getActionList(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ActionPlanListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ActionPlanListBean> baseBean) {
                        getView().getActionPlanListSuccess(baseBean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
