package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.IndexTreeListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
public interface IndexTreeView extends MvpView {
    /**
     * 获取指标树成功
     * @param resdata
     */
    void getIndexTreeSuccess(IndexTreeListBean resdata);
}
