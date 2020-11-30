package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.TableInfoBean;
import com.datacvg.sempmobile.bean.TableParamInfoBean;
import com.datacvg.sempmobile.bean.TableParamInfoListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-26
 * @Description :
 */
public interface TableDetailView extends MvpView {
    /**
     * 获取报表参数成功
     * @param tableParamInfoListBean
     */
    void getParamInfoSuccess(TableParamInfoListBean tableParamInfoListBean);

    /**
     * 获取报表配置信息成功
     * @param resdata
     */
    void getTableInfoSuccess(TableInfoBean resdata);
}
