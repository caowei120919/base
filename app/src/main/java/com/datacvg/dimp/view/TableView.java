package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.TableListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface TableView extends MvpView {
    /**
     * 获取报表列表成功
     * @param tableBeans
     */
    void getTableSuccess(TableListBean tableBeans);

    /**
     * 获取图片资源成功
     * @param res_id
     * @param resdata
     */
    void getImageResSuccess(String res_id, String resdata);
}
