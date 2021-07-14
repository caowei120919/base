package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.PageItemBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-13
 * @Description :
 */
@Keep
public class CheckIndexEvent {
    private PageItemBean pageItemBean ;
    public CheckIndexEvent(PageItemBean bean) {
        this.pageItemBean = bean ;
    }

    public PageItemBean getPageItemBean() {
        return pageItemBean;
    }

    public void setPageItemBean(PageItemBean pageItemBean) {
        this.pageItemBean = pageItemBean;
    }
}
