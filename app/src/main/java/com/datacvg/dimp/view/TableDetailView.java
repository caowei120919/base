package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-26
 * @Description :
 */
public interface TableDetailView extends MvpView {
    /**
     * 获取报表参数成功
     * @param tableParamInfoListBean
     */
    void getParamInfoSuccess(TableParamInfoListBean tableParamInfoListBean);

    /**
     * 获取报表配置信息成功
     * @param resdata
     */
    void getTableInfoSuccess(TableInfoBean resdata);

    /**
     * 评论成功
     */
    void submitCommentsSuccess();

    /**
     * 获取报表评论成功
     * @param resdata
     */
    void getCommentsSuccess(CommentListBean resdata);
}
