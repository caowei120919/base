package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.UserJobsListBean;
import com.datacvg.dimp.view.PersonView;

import java.util.Map;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class PersonPresenter extends BasePresenter<PersonView> {
    MobileApi api ;

    @Inject
    public PersonPresenter(MobileApi api) {
        this.api = api ;
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        api.loginOut()
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        PLog.e("LoginOut is Complete");
                        getView().loginOutSuccess();
                    }

                    @Override
                    public void onNext(BaseBean bean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
    }

    /**
     * 获取用户关联岗位信息
     * @param userPkid 用户标识id
     */
    public void getJob(String userPkid) {
        api.getJob(userPkid)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<UserJobsListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<UserJobsListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getUseJobsSuccess(bean.getResdata());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        PLog.e("TAG",e.getMessage());
                    }
                });
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
                        if(checkJsonCode(bean)){
                            getView().getMessageSuccess(bean.getResdata());
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
     * 上传用户头像信息
     * @param params
     */
    public void uploadAvatar(Map params) {
        api.uploadAvatar(PreferencesHelper.get(Constants.USER_ID,""),params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if(checkJsonCode(bean)){
                            ToastUtils.showLongToast(bean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
