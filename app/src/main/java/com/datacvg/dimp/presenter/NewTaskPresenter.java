package com.datacvg.dimp.presenter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.CreateTaskBean;
import com.datacvg.dimp.view.NewTaskView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 创建任务
     * @param actionPlanInfoDTO
     */
    public void createTask(CreateTaskBean actionPlanInfoDTO) {
        Map childMap = new Gson().fromJson(new Gson().toJson(actionPlanInfoDTO),Map.class);
        api.createTask(childMap)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(bean.getStatus() == Constants.SERVICE_CODE_SUCCESS_MOBILE){
                            getView().createTaskSuccess();
                        }else{
                            getView().createTaskFailed(bean.getMessage());
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
