package com.datacvg.dimp.baseandroid.download.downinterface;

/**
 * FileName: DownloadStub
 * Author: 曹伟
 * Date: 2019/9/16 15:04
 * Description:
 */

public interface DownloadStub {
    void start();

    void pause();

    void cancel();

    boolean isRunning();
}
