package com.datacvg.sempmobile.baseandroid.room;

import android.content.Context;

import androidx.annotation.NonNull;

import com.datacvg.sempmobile.baseandroid.utils.ThreadUtils;

import java.util.List;

import io.reactivex.Flowable;

/**
 * FileName: DebugInfoDataSource
 * Author: 曹伟
 * Date: 2019/9/16 16:23
 * Description:
 */

public class DebugInfoDataSource {

    private static volatile DebugInfoDataSource INSTANCE;

    private DebugInfoDao mDebugInfoDao;

    DebugInfoDataSource(DebugInfoDao debugInfoDao) {
        mDebugInfoDao = debugInfoDao;
    }

    public static DebugInfoDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (DebugInfoDataSource.class) {
                if (INSTANCE == null) {
                    RoomDataBase database = RoomDataBase.getInstance(context);
                    INSTANCE = new DebugInfoDataSource(database.debugInfoDao());
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<List<DebugInfo>> getDebugInfo() {
        return mDebugInfoDao.loadAll();
    }

    public void insertOrUpdateDebugInfo(DebugInfo debugInfo) {
        mDebugInfoDao.insertDebugInfo(debugInfo);
    }

    public void deleteAllDebugInfo() {
        // android room Cannot access database on the main thread since it may
        // potentially lock the UI for a long period of time.
        ThreadUtils.newSingleThreadExecutor(DebugInfo.class.getSimpleName())
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        mDebugInfoDao.deleteAllDebugInfo();
                    }
                });
    }
}