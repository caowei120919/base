package com.datacvg.sempmobile.baseandroid.download.downinterface;


import com.datacvg.sempmobile.baseandroid.download.DownloadException;

/**
 * FileName: OnConnectListener
 * Author: 曹伟
 * Date: 2019/9/16 15:07
 * Description:
 */

public interface OnConnectListener {

    void onConnecting();

    void onConnected(long time, long length, boolean isAcceptRanges);

    void onConnectCanceled();

    void onConnectFailed(DownloadException de);
}
