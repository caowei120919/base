package com.datacvg.dimp.baseandroid.download.downinterface;

/**
 * FileName: DownloadTask
 * Author: 曹伟
 * Date: 2019/9/16 15:10
 * Description:
 */

public interface DownloadTask extends Runnable {

    boolean isDownloading();

    boolean isPaused();

    boolean isCanceled();

    boolean isComplete();

    boolean isFailed();

    void pause();

    void cancel();
}
