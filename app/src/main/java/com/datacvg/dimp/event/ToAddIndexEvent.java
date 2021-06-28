package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.PageItemBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
@Keep
public class ToAddIndexEvent {
    private PageItemBean bean ;

    public PageItemBean getBean() {
        return bean;
    }

    public void setBean(PageItemBean bean) {
        this.bean = bean;
    }

    public ToAddIndexEvent(PageItemBean bean) {
        this.bean = bean ;
    }
}
