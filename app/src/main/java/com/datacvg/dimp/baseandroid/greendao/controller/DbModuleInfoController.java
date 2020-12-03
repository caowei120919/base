package com.datacvg.dimp.baseandroid.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.datacvg.dimp.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.dimp.baseandroid.greendao.bean.DaoSession;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-27
 * @Description :
 */
public class DbModuleInfoController {
    private static DbModuleInfoController mInstance ;
    private DaoMaster.DevOpenHelper openHelper ;
    private Context mContext ;
    private String dbName = "db_module" ;

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    private DbModuleInfoController(Context context) {
        this.mContext = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbModuleInfoController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbModuleInfoController.class) {
                if (mInstance == null) {
                    mInstance = new DbModuleInfoController(context);
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
     * @param moduleInfo
     */
    public void insertModuleInfo(ModuleInfo moduleInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao moduleInfoDao = daoSession.getModuleInfoDao();
        moduleInfoDao.insert(moduleInfo);
    }

    /**
     * 去除个人中心，可选择模块
     * @return
     */
    public List<ModuleInfo> getPermissionModuleList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao moduleInfoDao = daoSession.getModuleInfoDao();
        QueryBuilder<ModuleInfo> qb = moduleInfoDao.queryBuilder();
        qb.where(ModuleInfoDao.Properties.Module_permission.eq(true)
                ,ModuleInfoDao.Properties.Module_id.notEq(5));
        List<ModuleInfo> list = qb.list();
        return list;
    }

    public List<ModuleInfo> getSelectedModuleList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao moduleInfoDao = daoSession.getModuleInfoDao();
        QueryBuilder<ModuleInfo> qb = moduleInfoDao.queryBuilder();
        qb.where(ModuleInfoDao.Properties.Module_checked.eq(true));
        List<ModuleInfo> list = qb.list();
        return list;
    }

    public List<ModuleInfo> getModuleList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao moduleInfoDao = daoSession.getModuleInfoDao();
        QueryBuilder<ModuleInfo> qb = moduleInfoDao.queryBuilder();
        List<ModuleInfo> list = qb.list();
        return list;
    }

    public void updateModuleInfo(ModuleInfo moduleInfo){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao moduleInfoDao = daoSession.getModuleInfoDao();
        moduleInfoDao.update(moduleInfo);
    }

    public void updateModuleInfoAll(List<ModuleInfo> infos){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao moduleInfoDao = daoSession.getModuleInfoDao();
        moduleInfoDao.updateInTx(infos);
    }

    public ModuleInfo getModule(String res_id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ModuleInfoDao userDao = daoSession.getModuleInfoDao();
        QueryBuilder<ModuleInfo> qb = userDao.queryBuilder();
        qb.where(ModuleInfoDao.Properties.Module_res_id.eq(res_id));
        ModuleInfo departmentBean = qb.build().unique();
        return departmentBean ;
    }
}
