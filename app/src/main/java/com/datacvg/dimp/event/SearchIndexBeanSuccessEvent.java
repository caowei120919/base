package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.SearchIndexBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-14
 * @Description :
 */
@Keep
public class SearchIndexBeanSuccessEvent {
    private SearchIndexBean searchIndexBean ;
    public SearchIndexBeanSuccessEvent(SearchIndexBean searchIndexBean) {
        this.searchIndexBean = searchIndexBean ;
    }

    public SearchIndexBean getSearchIndexBean() {
        return searchIndexBean;
    }

    public void setSearchIndexBean(SearchIndexBean searchIndexBean) {
        this.searchIndexBean = searchIndexBean;
    }
}
