package com.datacvg.sempmobile.baseandroid.download;


import com.datacvg.sempmobile.baseandroid.download.downinterface.DownLoadCallBack;

import java.io.File;


/**
 * FileName: SimpleDownLoadCallBack
 * Author: 曹伟
 * Date: 2019/10/11 12:07
 * Description:
 */

public abstract class SimpleDownLoadCallBack implements DownLoadCallBack {

    @Override
    public void onConnected(long total, boolean isRangeSupport) {

    }

    @Override
    public void onPaused(File downloadfile) {

    }
}
