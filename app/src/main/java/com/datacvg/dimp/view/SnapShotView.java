package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.KpiPermissionDataBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-03
 * @Description :
 */
public interface SnapShotView extends MvpView {
    /**
     * 获取自定义阈值名称成功
     * @param data
     */
    void getKpiPermissionSuccess(KpiPermissionDataBean data);

    /**
     * 获取自定义阈值名称失败
     */
    void getKpiPermissionFailed();
}
