package com.datacvg.dimp.socket.listener;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-19
 * @Description : 大屏投放websocket链接监听
 */
public interface ScreenWebSocketListener {
    /**
     * 建立链接
     * @param webSocket
     * @param response
     */
    void onOpen(WebSocket webSocket, Response response);

    /**
     * 链接关闭
     * @param webSocket
     * @param code
     * @param reason
     */
    void onClosed(WebSocket webSocket, int code, String reason);

    /**
     * 链接关闭中
     * @param webSocket
     * @param code
     * @param reason
     */
    void onClosing(WebSocket webSocket, int code, String reason);

    /**
     * 链接失败
     * @param webSocket
     * @param t
     * @param response
     */
    void onFailure(WebSocket webSocket, Throwable t, Response response);

    /**
     * 收到消息
     * @param webSocket
     * @param text
     */
    void onMessage(WebSocket webSocket, String text);

    /**
     * 收到消息
     * @param webSocket
     * @param bytes
     */
    void onMessage(WebSocket webSocket, ByteString bytes);
}
