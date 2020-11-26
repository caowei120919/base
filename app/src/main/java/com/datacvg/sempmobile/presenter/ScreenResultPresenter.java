package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.view.ScreenResultView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-19
 * @Description :
 */
public class ScreenResultPresenter extends BasePresenter<ScreenResultView>{

    MobileApi api ;

    @Inject
    public ScreenResultPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 大屏播放
     * @param deviceId
     * @param clientIp
     * @param scPlayTime
     * @param scPlayInfo
     * @param scPlayStatus
     * @param scIndexStatus
     * @param code
     * @param targetIp
     */
    public void confirmOnTheScreen(String deviceId, String clientIp, String scPlayTime, String scPlayInfo
            , String scPlayStatus, String scIndexStatus, String code, String targetIp) {
        Map<String,String> params = new HashMap<>();
        params.put("deviceId",deviceId);
        params.put("clientIp",clientIp);
        params.put("scPlayTime",scPlayTime);
        params.put("scPlayInfo",scPlayInfo);
        params.put("scPlayStatus",scPlayStatus);
        params.put("scIndexStatus",scIndexStatus);
        params.put("code",code);
        params.put("targetIp",targetIp);
        api.confirmOnTheScreen(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<String>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<String> bean) {
                        switch (bean.getStatus()){
                            /**
                             * 投屏失败
                             */
                            case -1 :
                                getView().forScreenFailed();
                                break;

                            /**
                             * 投屏成功
                             */
                            case 2000 :
                                getView().forScreenSuccess();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
