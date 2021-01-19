package com.datacvg.dimp.presenter;

import com.datacvg.dimp.baseandroid.config.MobileApi;
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
}
