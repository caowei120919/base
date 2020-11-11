package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.MessageBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-10
 * @Description :
 */
public interface MessageListView extends MvpView {
    /**
     * 获取消息成功
     * @param resdata
     */
    void getMessageSuccess(MessageBean resdata);

    /**
     * 设置消息已读成功
     */
    void getMessageReadSuccess();

}
