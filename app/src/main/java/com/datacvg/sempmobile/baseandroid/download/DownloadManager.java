package com.datacvg.sempmobile.baseandroid.download;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.datacvg.sempmobile.baseandroid.download.downinterface.DownLoadCallBack;
import com.datacvg.sempmobile.baseandroid.download.downinterface.DownloadStub;
import com.datacvg.sempmobile.baseandroid.download.downinterfaceimpl.DownloadStubImpl;
import com.datacvg.sempmobile.baseandroid.utils.DigestUtils;
import com.datacvg.sempmobile.baseandroid.utils.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * FileName: DownloadManager
 * Author: 曹伟
 * Date: 2019/9/16 15:02
 * Description:
 */

public class DownloadManager {

    public static final String TAG = DownloadManager.class.getSimpleName();

    private static DownloadManager sDownloadManager;

    private Map<String, DownloadStub> mDownloaderMap;

    private DownloadConfiguration mConfig;

    private ExecutorService mExecutorService;

    private Handler mMainHandler;

    private DownloadManager() {
        mDownloaderMap = new LinkedHashMap<String, DownloadStub>();
    }

    public static DownloadManager getInstance() {
        if (sDownloadManager == null) {
            synchronized (DownloadManager.class) {
                sDownloadManager = new DownloadManager();
            }
        }
        return sDownloadManager;
    }

    public void init(Context context) {
        init(context, new DownloadConfiguration());
    }

    public void init(Context context, @NonNull DownloadConfiguration config) {
        if (config.getThreadNum() > config.getMaxThreadNum()) {
            throw new IllegalArgumentException("thread num must < max thread num");
        }
        mConfig = config;
        mExecutorService = Executors.newFixedThreadPool(mConfig.getMaxThreadNum());
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public synchronized void download(String uri, File folder, String filename, boolean allowSame, DownLoadCallBack downLoadCallBack) {
        String key = DigestUtils.md5Hex(uri);
        if (check(key)) {
            if (allowSame) {
                // multi download the same file
                key = UUID.randomUUID().toString() + "-" + key;
                filename = UUID.randomUUID().toString() + "-" + filename;
                DownloadStub downloader = new DownloadStubImpl(uri, folder, filename, downLoadCallBack, mExecutorService, key, mConfig);
                mDownloaderMap.put(key, downloader);
                downloader.start();
            }
        } else {
            DownloadStub downloader = new DownloadStubImpl(uri, folder, filename, downLoadCallBack, mExecutorService, key, mConfig);
            mDownloaderMap.put(key, downloader);
            downloader.start();
        }
    }

    public synchronized void pause(String key) {
        for (Map.Entry<String, DownloadStub> itemMap : mDownloaderMap.entrySet()) {
            if (StringUtils.startsWithIgnoreCase(itemMap.getKey(), key)) {
                DownloadStub downloader = itemMap.getValue();
                if (downloader != null && downloader.isRunning()) {
                    downloader.pause();
                }
            }
        }
    }

    public synchronized void cancel(String key) {
        for (Map.Entry<String, DownloadStub> itemMap : mDownloaderMap.entrySet()) {
            if (StringUtils.startsWithIgnoreCase(itemMap.getKey(), key)) {
                DownloadStub downloader = itemMap.getValue();
                if (downloader != null) {
                    downloader.cancel();
                }
            }
        }
    }

    public synchronized void deleteKey(String key) {
        if (check(key)) {
            DownloadStub downloader = mDownloaderMap.get(key);
            if (downloader != null) {
                mDownloaderMap.remove(key);
            }
        }
    }

    public synchronized boolean check(String key) {
        return mDownloaderMap.containsKey(key);
    }

    public Handler getMainHandler() {
        return mMainHandler;
    }
}