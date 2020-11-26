package com.datacvg.sempmobile.socket;

import com.datacvg.sempmobile.socket.listener.ScreenWebSocketListener;
import javax.annotation.Nullable;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-19
 * @Description :
 */
public class ScreenWebSocket extends WebSocketListener {

    private static ScreenWebSocketListener mListener;

    private ScreenWebSocket() {
    }

    private static class InstanceHolder {
        private static final ScreenWebSocket INSTANCE = new ScreenWebSocket();
    }

    public static ScreenWebSocket get(ScreenWebSocketListener listener) {
        mListener = listener;
        return ScreenWebSocket.InstanceHolder.INSTANCE;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        mListener.onOpen(webSocket, response);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        mListener.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        mListener.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        mListener.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        mListener.onMessage(webSocket, text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        mListener.onMessage(webSocket, bytes);
    }
}
