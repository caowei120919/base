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
     * 心动方案创建成功
     */
    void createTaskSuccess();

    /**
     * 行动方案创建失败
     * @param message
     */
    void createTaskFailed(String message);
}
