package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.ModuleListBean;

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
}
