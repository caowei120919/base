package com.datacvg.sempmobile.baseandroid.download.downinterfaceimpl;

import android.os.Process;

import com.datacvg.sempmobile.baseandroid.download.DownLoadConstants;
import com.datacvg.sempmobile.baseandroid.download.DownloadException;
import com.datacvg.sempmobile.baseandroid.download.DownloadInfo;
import com.datacvg.sempmobile.baseandroid.download.DownloadStatus;
import com.datacvg.sempmobile.baseandroid.download.downinterface.DownloadTask;
import com.datacvg.sempmobile.baseandroid.download.downinterface.OnDownloadListener;
import com.datacvg.sempmobile.baseandroid.greendao.bean.ThreadInfo;
import com.datacvg.sempmobile.baseandroid.utils.javaio.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


/**
 * FileName: DownloadTaskImpl
 * Author: 曹伟
 * Date: 2019/9/16 15:37
 * Description:
 */

public abstract class DownloadTaskImpl implements DownloadTask {
    private final DownloadInfo mDownloadInfo;
    private final ThreadInfo mThreadInfo;
    private final OnDownloadListener mOnDownloadListener;

    private volatile int mStatus;

    private volatile int mCommend = 0;

    public DownloadTaskImpl(DownloadInfo downloadInfo, ThreadInfo threadInfo, OnDownloadListener listener) {
        this.mDownloadInfo = downloadInfo;
        this.mThreadInfo = threadInfo;
        this.mOnDownloadListener = listener;
        this.mStatus = DownloadStatus.STATUS_STARTED;
    }

    @Override
    public boolean isDownloading() {
        return mStatus == DownloadStatus.STATUS_PROGRESS || mStatus == DownloadStatus.STATUS_STARTED;
    }

    @Override
    public boolean isPaused() {
        return mStatus == DownloadStatus.STATUS_PAUSED;
    }

    @Override
    public boolean isCanceled() {
        return mStatus == DownloadStatus.STATUS_CANCELED;
    }

    @Override
    public boolean isComplete() {
        return mStatus == DownloadStatus.STATUS_COMPLETED;
    }

    @Override
    public boolean isFailed() {
        return mStatus == DownloadStatus.STATUS_FAILED;
    }

    @Override
    public void pause() {
        mCommend = DownloadStatus.STATUS_PAUSED;
    }

    @Override
    public void cancel() {
        mCommend = DownloadStatus.STATUS_CANCELED;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        mStatus = DownloadStatus.STATUS_PROGRESS;
        try {
            if (mThreadInfo.getFinished() > 0 && (mThreadInfo.getFinished() == (mThreadInfo
                    .getEndoffset() - mThreadInfo.getStartoffset() + 1) || mThreadInfo.getFinished() == (mThreadInfo
                    .getEndoffset() - mThreadInfo.getStartoffset()))) {
                synchronized (mOnDownloadListener) {
                    mOnDownloadListener.onDownloadProgress(mDownloadInfo.getFinished(), mDownloadInfo
                            .getLength());
                    mStatus = DownloadStatus.STATUS_COMPLETED;
                    mOnDownloadListener.onDownloadCompleted();
                }
            } else {
                insertIntoDB(mThreadInfo);
                executeDownload();
                synchronized (mOnDownloadListener) {
                    updateDB(mThreadInfo);
                    mStatus = DownloadStatus.STATUS_COMPLETED;
                    mOnDownloadListener.onDownloadCompleted();
                }
            }
        } catch (DownloadException e) {
            handleDownloadException(e);
        }
    }

    private void handleDownloadException(DownloadException e) {
        switch (e.getErrorCode()) {
            case DownloadStatus.STATUS_PAUSED:
                synchronized (mOnDownloadListener) {
                    mStatus = DownloadStatus.STATUS_PAUSED;
                    mOnDownloadListener.onDownloadPaused();
                }
                break;

            case DownloadStatus.STATUS_CANCELED:
                synchronized (mOnDownloadListener) {
                    mStatus = DownloadStatus.STATUS_CANCELED;
                    mOnDownloadListener.onDownloadCanceled();
                }
                break;

            case DownloadStatus.STATUS_FAILED:
                synchronized (mOnDownloadListener) {
                    mStatus = DownloadStatus.STATUS_FAILED;
                    mOnDownloadListener.onDownloadFailed(e);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown state");
        }
    }

    private void executeDownload() throws DownloadException {
        final URL url;
        try {
            url = new URL(mThreadInfo.getUri());
        } catch (MalformedURLException e) {
            throw new DownloadException(DownloadStatus.STATUS_FAILED, "Bad url.", e);
        }

        HttpURLConnection httpConnection = null;
        try {
            int redirectTimes = 0;
            String location;
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(DownLoadConstants.HTTP.CONNECT_TIME_OUT);
            httpConnection.setReadTimeout(DownLoadConstants.HTTP.READ_TIME_OUT);
            httpConnection.setRequestMethod(DownLoadConstants.HTTP.GET);
            setHttpHeader(getHttpHeaders(mThreadInfo), httpConnection);
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == getResponseCode()) {
                transferData(httpConnection);
            } else {
                if (isRedirection(responseCode)) {
                    while (isRedirection(responseCode)) {
                        location = httpConnection.getHeaderField("Location");
                        if (location == null) {
                            throw new DownloadException(DownloadStatus.STATUS_FAILED, "Location is null");
                        }
                        httpConnection.disconnect();
                        httpConnection = (HttpURLConnection) new URL(location).openConnection();
                        httpConnection.setConnectTimeout(DownLoadConstants.HTTP.CONNECT_TIME_OUT);
                        httpConnection.setReadTimeout(DownLoadConstants.HTTP.READ_TIME_OUT);
                        httpConnection.setRequestMethod(DownLoadConstants.HTTP.GET);
                        setHttpHeader(getHttpHeaders(mThreadInfo), httpConnection);
                        responseCode = httpConnection.getResponseCode();
                        redirectTimes++;
                        if (redirectTimes >= 3) {
                            throw new DownloadException(DownloadStatus.STATUS_FAILED, "Max redirection done");
                        }
                    }
                    if (responseCode == getResponseCode()) {
                        transferData(httpConnection);
                    } else {
                        throw new DownloadException(DownloadStatus.STATUS_FAILED, "UnSupported " + "response code:" + responseCode);
                    }
                } else {
                    throw new DownloadException(DownloadStatus.STATUS_FAILED, "UnSupported " + "response code:" + responseCode);
                }
            }
        } catch (ProtocolException e) {
            throw new DownloadException(DownloadStatus.STATUS_FAILED, "Protocol error", e);
        } catch (IOException e) {
            throw new DownloadException(DownloadStatus.STATUS_FAILED, "IO error", e);
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }

    private void setHttpHeader(Map<String, String> headers, URLConnection connection) {
        if (headers != null) {
            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
        }
    }

    private void transferData(HttpURLConnection httpConnection) throws DownloadException {
        InputStream inputStream = null;
        RandomAccessFile raf = null;
        try {
            try {
                inputStream = httpConnection.getInputStream();
            } catch (IOException e) {
                throw new DownloadException(DownloadStatus.STATUS_FAILED, "http get " + "inputStream error", e);
            }
            final long offset = mThreadInfo.getStartoffset() + mThreadInfo.getFinished();
            try {
                raf = getFile(mDownloadInfo.getDir(), mDownloadInfo.getName(), offset);
            } catch (IOException e) {
                throw new DownloadException(DownloadStatus.STATUS_FAILED, "File error", e);
            }
        } finally {
            if (raf != null) {
                transferData(inputStream, raf);
            }
            StreamUtil.close(inputStream);
            StreamUtil.close(raf);
        }
    }

    private void transferData(InputStream inputStream, RandomAccessFile raf) throws
            DownloadException {
        final byte[] buffer = new byte[64 * 1024];
        while (true) {
            checkPausedOrCanceled();
            int len = -1;
            try {
                len = inputStream.read(buffer);
            } catch (IOException e) {
                throw new DownloadException(DownloadStatus.STATUS_FAILED, "Http " + "inputStream read error", e);
            }

            if (len == -1) {
                break;
            }

            try {
                raf.write(buffer, 0, len);
                mThreadInfo.setFinished(mThreadInfo.getFinished() + len);
                updateDB(mThreadInfo);
                synchronized (mOnDownloadListener) {
                    mDownloadInfo.setFinished(mDownloadInfo.getFinished() + len);
                    mOnDownloadListener.onDownloadProgress(mDownloadInfo.getFinished(), mDownloadInfo
                            .getLength());
                }
            } catch (IOException e) {
                throw new DownloadException(DownloadStatus.STATUS_FAILED, "Fail write " + "buffer to file", e);
            }
        }
    }

    private void checkPausedOrCanceled() throws DownloadException {
        if (mCommend == DownloadStatus.STATUS_CANCELED) {
            throw new DownloadException(DownloadStatus.STATUS_CANCELED, "Download " + "canceled!");
        } else if (mCommend == DownloadStatus.STATUS_PAUSED) {
            updateDB(mThreadInfo);
            throw new DownloadException(DownloadStatus.STATUS_PAUSED, "Download paused!");
        }
    }

    protected abstract void insertIntoDB(ThreadInfo info);

    protected abstract int getResponseCode();

    protected abstract void updateDB(ThreadInfo info);

    protected abstract Map<String, String> getHttpHeaders(ThreadInfo info);

    protected abstract RandomAccessFile getFile(File dir, String name, long offset) throws
            IOException;

    public static final int HTTP_TEMPORARY_REDIRECT = 307;
    public static final int HTTP_PERMANENT_REDIRECT = 308;

    private boolean isRedirection(int code) {
        return code == HttpURLConnection.HTTP_MOVED_PERM || code == HttpURLConnection.HTTP_MOVED_TEMP || code == HttpURLConnection.HTTP_SEE_OTHER || code == HttpURLConnection.HTTP_MULT_CHOICE || code == HTTP_TEMPORARY_REDIRECT || code == HTTP_PERMANENT_REDIRECT;
    }

}