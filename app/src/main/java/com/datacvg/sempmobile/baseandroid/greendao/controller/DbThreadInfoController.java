package com.datacvg.sempmobile.baseandroid.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.datacvg.sempmobile.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.sempmobile.baseandroid.greendao.bean.DaoSession;
import com.datacvg.sempmobile.baseandroid.greendao.bean.DebugInfo;
import com.datacvg.sempmobile.baseandroid.greendao.bean.DebugInfoDao;
import com.datacvg.sempmobile.baseandroid.greendao.bean.ThreadInfo;
import com.datacvg.sempmobile.baseandroid.greendao.bean.ThreadInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-24
 * @Description : bug数据库存储
 */
public class DbThreadInfoController {
    private static DbThreadInfoController mInstance ;
    private DaoMaster.DevOpenHelper openHelper ;
    private Context mContext ;
    private String dbName = "de_thread" ;

    private DbThreadInfoController(Context context) {
        this.mContext = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbThreadInfoController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbThreadInfoController.class) {
                if (mInstance == null) {
                    mInstance = new DbThreadInfoController(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(mContext, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(mContext, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入
     *
     * @param debugInfo
     */
    public void insertThreadInfo(ThreadInfo debugInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ThreadInfoDao threadInfoDao = daoSession.getThreadInfoDao();
        threadInfoDao.insert(debugInfo);
    }

    public List<ThreadInfo> getThreadInfo(String mTag) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ThreadInfoDao threadInfoDao = daoSession.getThreadInfoDao();
        QueryBuilder<ThreadInfo> qb = threadInfoDao.queryBuilder();
        qb.where(ThreadInfoDao.Properties.Tag.eq(mTag))
                .orderAsc(ThreadInfoDao.Properties.Id);
        List<ThreadInfo> list = qb.list();
        return list;
    }

    public void deleteThreadInfo(String mTag) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ThreadInfoDao threadInfoDao = daoSession.getThreadInfoDao();
        QueryBuilder<ThreadInfo> qb = threadInfoDao.queryBuilder();
        qb.where(ThreadInfoDao.Properties.Tag.eq(mTag))
                .orderAsc(ThreadInfoDao.Properties.Id);
        List<ThreadInfo> list = qb.list();
        for (ThreadInfo info : list){
            threadInfoDao.delete(info);
        }
    }
}
