package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.view.TableCommentView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-01-05
 * @Description :
 */
public class TableCommentPresenter extends BasePresenter<TableCommentView>{
    MobileApi api ;

    @Inject
    public TableCommentPresenter(MobileApi api) {
        this.api = api;
    }

    /**
     * 获取报表评论
     * @param resId
     * @param params
     */
    public void getTableComment(String resId, String params) {
        api.getTableComment(resId,params)
                .compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean<CommentListBean>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean<CommentListBean> bean) {
                        if(checkJsonCode(bean)){
                            getView().getTableCommentSuccess(bean.getData());
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
