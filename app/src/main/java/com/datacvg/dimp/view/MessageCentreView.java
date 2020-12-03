package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.MessageBean;

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
