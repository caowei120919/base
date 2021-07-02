package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.PageItemBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public interface BoardView extends MvpView {
    /**
     * 获取分页信息成功
     * @param pageItemBeans
     */
    void getPageSuccess(List<PageItemBean> pageItemBeans);
}
