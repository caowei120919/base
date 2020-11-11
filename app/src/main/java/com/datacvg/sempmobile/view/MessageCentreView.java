package com.datacvg.sempmobile.view;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.datacvg.sempmobile.bean.MessageBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public interface MessageCentreView extends MvpView {
    /**
     * 获取消息成功
     * @param resdata
     */
    void getMessageSuccess(MessageBean resdata);
}
