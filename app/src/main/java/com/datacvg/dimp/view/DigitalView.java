package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.PageItemListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public interface DigitalView extends MvpView {
    /**
     * 获取数字神经多页数据成功
     * @param pageItemBeans
     */
    void getDigitalPageSuccess(PageItemListBean pageItemBeans);

    /**
     * 删除页
     * @param deletePage
     */
    void deletePageSuccess(Boolean deletePage);
}
