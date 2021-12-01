package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.SetDefaultResBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableParamInfoBean;

import java.util.List;

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
    void getParamInfoSuccess(List<TableParamInfoBean> tableParamInfoListBean);

    /**
     * 获取报表配置信息成功
     * @param resdata
     */
    void getTableInfoSuccess(TableInfoBean resdata);

    /**
     * 设置默认报表成功
     * @param baseBean
     */
    void setDefaultReportSuccess(SetDefaultResBean baseBean);

    /**
     * 取消默认报表成功
     */
    void cancelDefaultReportSuccess();

    /**
     * 报表参数获取失败
     */
    void getParamInfoError();
}
