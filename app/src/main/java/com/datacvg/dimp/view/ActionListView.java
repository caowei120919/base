package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ActionForIndexBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public interface ActionListView extends MvpView {
    /**
     * 根据指标获取行动方案成功
     * @param data
     */
    void getActionForIndexSuccess(ActionForIndexBean data);
}
