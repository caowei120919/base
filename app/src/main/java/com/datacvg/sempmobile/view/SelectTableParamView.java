package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.TableParamInfoListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description :
 */
public interface SelectTableParamView extends MvpView {
    /**
     * 获取报表参数成功
     * @param resdata
     */
    void getParamInfoSuccess(TableParamInfoListBean resdata);
}
