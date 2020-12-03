package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-11
 * @Description :
 */
@Keep
public class ReadMessageBean {

    /**
     * message_id : [{"id":"1029386562741002438395"}]
     * state : read
     */

    private String state;
    private List<MessageIdBean> message_id;

    @Keep
    public static class MessageIdBean {
        /**
         * id : 1029386562741002438395
         */

        private String id;
    }
}
