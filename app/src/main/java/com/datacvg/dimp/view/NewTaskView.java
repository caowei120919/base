package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.IndexBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-09
 * @Description :
 */
public interface NewTaskView extends MvpView {
    /**
     * 获取指标成功
     * @param resdata
     */
    void getIndexSuccess(ActionPlanIndexListBean resdata);
}
