package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.MessageBean;
import com.datacvg.sempmobile.view.MessageCentreView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public class MessageCentrePresenter extends BasePresenter<MessageCentreView>{
    MobileApi api ;

    @Inject
    public MessageCentrePresenter(MobileApi api) {
        this.api = api;
    }

    public void getMessage(String pageIndex, String pageSize, String module_id, String read_flag) {
        api.getMessage(pageIndex,pageSize,module_id,read_flag)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<MessageBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<MessageBean> bean) {
                        getView().getMessageSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
