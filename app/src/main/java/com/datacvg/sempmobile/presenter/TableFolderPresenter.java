package com.datacvg.sempmobile.presenter;
import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.view.TableFolderView;

import javax.inject.Inject;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public class TableFolderPresenter extends BasePresenter<TableFolderView> {
    MobileApi api ;

    @Inject
    public TableFolderPresenter(MobileApi api) {
        this.api = api;
    }
}
