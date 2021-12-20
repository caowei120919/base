package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-17
 * @Description :
 */
public interface ChooseIndexFromActionView extends MvpView {
    void getIndexSuccess(ActionPlanIndexListBean resdata);
}
