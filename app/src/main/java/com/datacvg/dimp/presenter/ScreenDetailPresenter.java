package com.datacvg.dimp.presenter;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ScreenDetailBean;
import com.datacvg.dimp.view.ScreenDetailView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :
 */
public class ScreenDetailPresenter extends BasePresenter<ScreenDetailView> {

    MobileApi api ;

    @Inject
    public ScreenDetailPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取大屏详细信息
     * @param screen_id
     */
    public void getScreenDetail(String screen_id) {
        api.getScreenDetail(screen_id)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ScreenDetailBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<ScreenDetailBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getScreenDetailSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
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
                            case -1 :
                                getView().forScreenFailed(scPlayStatus);
                                break;

                            default :
                                getView().forScreenSuccess(scPlayStatus);
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
