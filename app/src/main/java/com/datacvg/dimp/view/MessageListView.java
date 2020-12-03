package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.MessageBean;

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
