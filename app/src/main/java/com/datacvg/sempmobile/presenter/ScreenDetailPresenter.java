package com.datacvg.sempmobile.presenter;
import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ScreenDetailBean;
import com.datacvg.sempmobile.view.ScreenDetailView;
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
                        getView().getScreenDetailSuccess(bean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
