package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.ModuleListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :
 */
public interface MainView extends MvpView {
    /**
     * 模块获取成功
     * @param resdata
     */
    void getModuleSuccess(ModuleListBean resdata);

    /**
     * 获取维度下联系人成功
     * @param resdata
     */
    void getDepartmentAndContactSuccess(DefaultUserListBean resdata);
}
