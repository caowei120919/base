package com.datacvg.sempmobile.presenter;

import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.bean.ImageResBean;
import com.datacvg.sempmobile.bean.ScreenDetailBean;
import com.datacvg.sempmobile.bean.TableListBean;
import com.datacvg.sempmobile.view.TableView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class TablePresenter extends BasePresenter<TableView>{

    MobileApi api ;

    @Inject
    public TablePresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 查询报表列表数据
     * @param tableType 设备类型 3标识app
     */
    public void getTableList(String tableType) {
        api.getTableList(tableType)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableListBean> bean) {
                        PLog.e("getTableList ==== " + bean.getResdata().size());
                        getView().getTableSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getImageRes(String res_id) {
        api.getImageRes(res_id)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<ImageResBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(ImageResBean bean) {
                        getView().getImageResSuccess(res_id,bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
