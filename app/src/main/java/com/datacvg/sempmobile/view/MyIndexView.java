package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.IndexBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-30
 * @Description :
 */
public interface MyIndexView extends MvpView {
    /**
     * 获取指标成功
     * @param resdata
     */
    void getIndexSuccess(IndexBean resdata);

    /**
     * 新修改指标成功
     */
    void changeIndexSuccess();

    /**
     * 修改指标失败
     */
    void changeIndexFail();
}
