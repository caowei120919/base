package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.AddToScreenRequestBean;
import com.datacvg.dimp.view.NewScreenView;
import com.google.gson.Gson;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :
 */
public class NewScreenPresenter extends BasePresenter<NewScreenView>{
    MobileApi api ;

    @Inject
    public NewScreenPresenter(MobileApi api) {
        this.api = api;
    }


    /**
     * 添加到大屏请求
     * @param requestBean
     */
    public void addToScreenRequest(AddToScreenRequestBean requestBean) {
        api.addToScreenRequest(new Gson().fromJson(new Gson().toJson(requestBean), Map.class))
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(checkJsonCode(bean)){
                            getView().addToScreenSuccess();
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
