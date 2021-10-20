package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.SearchListReportBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-20
 * @Description :
 */
public interface SearchReportView extends MvpView {
    /**
     * 获取搜索报告列表信息成功
     * @param data
     */
    void getSearchReportListSuccess(SearchListReportBean data);
}
