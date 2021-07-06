package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.config.UploadApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.ConstantReportBean;
import com.datacvg.dimp.bean.SetDefaultResBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.view.TableDetailView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-26
 * @Description :
 */
public class TableDetailPresenter extends BasePresenter<TableDetailView>{
    MobileApi api ;

    @Inject
    public TableDetailPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取报表参数信息
     * @param res_id
     */
    public void getResParamInfo(String res_id) {
        api.getResParamInfo(res_id)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableParamInfoListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableParamInfoListBean> bean) {
                        getView().getParamInfoSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    /**
     * 获取报表url
     * @param paramArr
     * @param resId
     */
    public void getTableUrl(String paramArr, String resId) {
        Map<String,String> params = new HashMap<>();
        params.put("paramStr",paramArr);
        params.put("res_id",resId);
        api.getTableUrl(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableInfoBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableInfoBean> bean) {
                        getView().getTableInfoSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    public void upload(Map<String, RequestBody> requestBodyMap) {
        api.uploadFile(requestBodyMap)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onNext(BaseBean baseBean) {

                    }
                });
    }

    /**
     * 获取报表相关评论
     * @param
     */
    public void getTableComment(String resId,String params) {
        api.getTableComment(resId,params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<CommentListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<CommentListBean> bean) {
                        getView().getCommentsSuccess(bean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    public void submitComments(Map<String, RequestBody> requestBodyMap) {
        api.submitComments(requestBodyMap)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.getStatus() == Constants.SERVICE_CODE_SUCCESS_FIS) {
                            getView().submitCommentsSuccess();
                        }
                    }
                });
    }

    public void getPowerBiInfo(String token) {
        api.getPowerBiInfo(token)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<String>(){
                    @Override
                    public void onNext(String baseBean) {
                        PLog.e(baseBean);
                    }
                });
    }

    /**
     * 设置为默认报表
     * @param map
     */
    public void setReportToDefault(Map map) {
        api.setReportToDefault(map)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<SetDefaultResBean>(){
                    @Override
                    public void onNext(SetDefaultResBean baseBean) {
                        if(baseBean.getResult()){
                            getView().setDefaultReportSuccess(baseBean);
                        }
                    }
                });
    }

    /**
     * 取消默认报表
     * @param res_pkid
     */
    public void cancelReportForDefault(String res_pkid) {
        api.cancelReportForDefault(res_pkid)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onNext(BaseBean baseBean) {
                        getView().cancelDefaultReportSuccess();
                    }
                });
    }
}
