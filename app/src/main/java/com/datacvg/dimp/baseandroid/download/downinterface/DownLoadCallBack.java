package com.datacvg.dimp.baseandroid.download.downinterface;


import com.datacvg.dimp.baseandroid.download.DownloadException;

import java.io.File;


/**
 * FileName: DownLoadCallBack
 * Author: 曹伟
 * Date: 2019/9/16 15:08
 * Description:
 */

public interface DownLoadCallBack {

    void onConnected(long total, boolean isRangeSupport);

    void onProgress(long finished, long total, int progress);

    void onCompleted(File downloadfile);

    void onFailed(DownloadException e);

    void onPaused(File downloadfile);
}
