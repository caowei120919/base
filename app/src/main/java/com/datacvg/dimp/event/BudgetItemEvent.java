package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.PageItemBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-28
 * @Description :
 */
@Keep
public class BudgetItemEvent {
    private PageItemBean pageItemBean ;
    public BudgetItemEvent(PageItemBean pageItemBean) {
        this.pageItemBean = pageItemBean ;
    }

    public PageItemBean getPageItemBean() {
        return pageItemBean;
    }

    public void setPageItemBean(PageItemBean pageItemBean) {
        this.pageItemBean = pageItemBean;
    }
}
