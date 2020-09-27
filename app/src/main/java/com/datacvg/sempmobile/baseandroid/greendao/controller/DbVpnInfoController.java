package com.datacvg.sempmobile.baseandroid.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.datacvg.sempmobile.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.sempmobile.baseandroid.greendao.bean.DaoSession;
import com.datacvg.sempmobile.baseandroid.greendao.bean.VpnInfo;
import com.datacvg.sempmobile.baseandroid.greendao.bean.VpnInfoDao;
/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-24
 * @Description :
 */
public class DbVpnInfoController {
    private static DbVpnInfoController mInstance ;
    private DaoMaster.DevOpenHelper openHelper ;
    private Context mContext ;
    private String dbName = "de_debug" ;

    private DbVpnInfoController(Context context) {
        this.mContext = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbVpnInfoController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbDebugInfoController.class) {
                if (mInstance == null) {
                    mInstance = new DbVpnInfoController(context);
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
     * @param vpnInfo
     */
    public void insertVpnInfo(VpnInfo vpnInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        VpnInfoDao vpnInfoDao = daoSession.getVpnInfoDao();
        vpnInfoDao.insert(vpnInfo);
    }
}
