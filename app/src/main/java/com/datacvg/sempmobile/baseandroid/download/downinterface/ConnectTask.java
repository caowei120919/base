package com.datacvg.sempmobile.baseandroid.download.downinterface;

/**
 * FileName: ConnectTask
 * Author: 曹伟
 * Date: 2019/9/16 15:10
 * Description:
 */

public interface ConnectTask extends Runnable {

    boolean isConnecting();

    boolean isConnected();

    boolean isCanceled();

    boolean isFailed();

    void cancel();
}

