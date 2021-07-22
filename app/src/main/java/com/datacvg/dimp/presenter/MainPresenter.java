package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.ConstantReportBean;
import com.datacvg.dimp.bean.DefaultReportBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.ModuleListBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.view.MainView;
import com.google.gson.Gson;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :
 */
public class MainPresenter extends BasePresenter<MainView>{
    MobileApi mobileApi ;

    @Inject
    public MainPresenter(MobileApi mobileApi ){
        this.mobileApi = mobileApi;
    }

    /**
     * 获取用户拥有权限的模块
     */
    public void getPermissionModule() {
        mobileApi.getPermissionModule()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<ModuleListBean>>(){
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(BaseBean<ModuleListBean> stringBaseBean) {
                        getView().getModuleSuccess(stringBaseBean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    /**
     * 获取默认报表相关信息
     * @param params
     */
    public void getDefaultReport(Map params) {
        mobileApi.getDefaultReport(params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<DefaultReportBean>(){
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(DefaultReportBean baseBean) {
                        if(baseBean != null){
                            ConstantReportBean reportBean
                                    = new ConstantReportBean(baseBean.getRes_id(),baseBean.getDefault_pkid());
                            getView().getDefaultReportSuccess(reportBean);
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
     * 获取所有维度和维度下用户信息
     */
    public void getDepartmentAndContact() {
        mobileApi.getDefaultUser().compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<DefaultUserListBean>>(){
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(BaseBean<DefaultUserListBean> defaultUserListBeanBaseBean) {
                        if(checkJsonCode(defaultUserListBeanBaseBean)){
                            getView().getDepartmentAndContactSuccess(defaultUserListBeanBaseBean.getResdata());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    public void getTableList(String tableType) {
        mobileApi.getTableList(tableType)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<TableListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<TableListBean> bean) {
                        getView().getTableSuccess(bean.getResdata());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }
}
