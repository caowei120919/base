package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.IndexDetailListBean;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description :
 */
public interface AddIndexView extends MvpView {
    /**
     * 推荐指标获取成功
     * @param indexChartRelation
     */
    void getRecommendIndexSuccess(List<IndexDetailListBean> indexChartRelation);

    /**
     * 获取所有指标成功
     * @param addAbleIndexInfo
     */
    void getAllIndexSuccess(List<IndexDetailListBean> addAbleIndexInfo);
}
