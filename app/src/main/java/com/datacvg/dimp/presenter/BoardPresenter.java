package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.bean.DigitalPageBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.bean.PageItemListBean;
import com.datacvg.dimp.view.BoardView;
import com.google.gson.Gson;
import java.util.List;
import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public class BoardPresenter extends BasePresenter<BoardView>{
    MobileApi api ;

    @Inject
    public BoardPresenter(MobileApi api) {
        this.api = api;
    }

    public void getBoardPage() {
        api.getDigitalPage().compose(RxUtils.applySchedulersLifeCycle(getView()))
                .subscribe(new RxObserver<BaseBean>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onNext(BaseBean boardPageBean) {
                        if(checkJsonCode(boardPageBean)){
                            DigitalPageBean digitalPageBean = new Gson()
                                    .fromJson(new Gson().toJson(boardPageBean.getData()),DigitalPageBean.class);
                            List<PageItemBean> pageItemBeans
                                    = new Gson().fromJson(digitalPageBean.getPositionPage(), PageItemListBean.class);
                            getView().getPageSuccess(pageItemBeans);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
