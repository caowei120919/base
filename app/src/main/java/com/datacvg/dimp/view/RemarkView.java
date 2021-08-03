package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.RemarkListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public interface RemarkView extends MvpView {
    /**
     * 获取备注信息成功
     * @param resdata
     */
    void getRemarkSuccess(RemarkListBean resdata);
}
