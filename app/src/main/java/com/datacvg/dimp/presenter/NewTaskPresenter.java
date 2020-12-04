package com.datacvg.dimp.presenter;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.IndexBean;
import com.datacvg.dimp.view.NewTaskView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-09
 * @Description :
 */
public class NewTaskPresenter extends BasePresenter<NewTaskView> {

    MobileApi api ;
    @Inject
    public NewTaskPresenter(MobileApi api) {
        this.api = api ;
    }


    /**
     * 获取行动方案指标数据
     */
    public void getActionPlanIndex() {
        api.getActionPlanIndex()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ActionPlanIndexListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ActionPlanIndexListBean> bean) {
                        getView().getIndexSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
