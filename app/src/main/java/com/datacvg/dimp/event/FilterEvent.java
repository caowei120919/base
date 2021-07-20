package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.PageItemBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-20
 * @Description :
 */
@Keep
public class FilterEvent {
    private PageItemBean pageItemBean ;

    public PageItemBean getPageItemBean() {
        return pageItemBean;
    }

    public void setPageItemBean(PageItemBean pageItemBean) {
        this.pageItemBean = pageItemBean;
    }

    public FilterEvent(PageItemBean pageItemBean) {
        this.pageItemBean = pageItemBean ;
    }
}
