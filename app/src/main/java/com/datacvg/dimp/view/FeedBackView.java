package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-16
 * @Description :
 */
public interface FeedBackView extends MvpView {
    /**
     * 反馈信息提交成功
     */
    void submitFeedBackSuccess();
}
