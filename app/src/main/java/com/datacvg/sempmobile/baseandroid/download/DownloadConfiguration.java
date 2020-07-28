package com.datacvg.sempmobile.baseandroid.download;

/**
 * FileName: DownloadConfiguration
 * Author: 曹伟
 * Date: 2019/9/16 15:01
 * Description:
 */

public class DownloadConfiguration {

    public static final int DEFAULT_MAX_THREAD_NUMBER = 15;

    public static final int DEFAULT_THREAD_NUMBER = 3;

    /**
     * thread number in the pool
     */
    private int maxThreadNum;

    /**
     * thread number for each download
     */
    private int threadNum;


    /**
     * init with default value
     */
    public DownloadConfiguration() {
        maxThreadNum = DEFAULT_MAX_THREAD_NUMBER;
        threadNum = DEFAULT_THREAD_NUMBER;
    }

    public int getMaxThreadNum() {
        return maxThreadNum;
    }

    public void setMaxThreadNum(int maxThreadNum) {
        this.maxThreadNum = maxThreadNum;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
