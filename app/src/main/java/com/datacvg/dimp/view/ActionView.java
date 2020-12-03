package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ActionPlanListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface ActionView extends MvpView {
    /**
     * 获取行动方案列表成功
     * @param ActionPlanBeans
     */
    void getActionPlanListSuccess(ActionPlanListBean ActionPlanBeans);
}
