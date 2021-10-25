package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.ScreenListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :
 */
public interface AddToScreenView extends MvpView {
    /**
     * 大屏列表获取成功
     * @param data
     */
    void getScreenSuccess(ScreenListBean data);
}
