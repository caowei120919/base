package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.CommentListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-01-05
 * @Description :
 */
public interface TableCommentView extends MvpView {
    /**
     * 获取报表评论成功
     * @param data
     */
    void getTableCommentSuccess(CommentListBean data);
}
