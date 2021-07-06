package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.TableListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public interface TableFolderView extends MvpView {

    /**
     * 获取报表列表成功
     * @param tableBeans
     */
    void getTableSuccess(TableListBean tableBeans);
}
