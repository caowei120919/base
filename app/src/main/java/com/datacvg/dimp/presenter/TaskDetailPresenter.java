package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.view.TaskDetailView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-17
 * @Description :
 */
public class TaskDetailPresenter extends BasePresenter<TaskDetailView>{
    MobileApi api ;

    @Inject
    public TaskDetailPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 查询行动方案详情
     * @param taskId
     * @param user_type
     * @param userId
     * @param language
     */
    public void getTaskInfo(String taskId, int user_type, String userId, String language) {
        api.getTaskInfo(taskId,user_type,userId,language)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TaskInfoBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TaskInfoBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getTaskInfoSuccess(bean.getResdata());
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
