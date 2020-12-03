package com.datacvg.dimp.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.datacvg.dimp.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.dimp.baseandroid.greendao.bean.DaoSession;
import com.datacvg.dimp.greendao.bean.RecentBean;
import com.datacvg.dimp.greendao.bean.RecentBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description : 最近联系人数据库简单的增删改查逻辑
 */
public class DbRecentController {
    private static DbRecentController mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private String dbName = "db_recent" ;

    private DbRecentController(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context,dbName,null);
    }

    public static DbRecentController getInstance(Context context){
        if(mInstance == null){
            synchronized (DbRecentController.class){}
            if(mInstance == null){
                mInstance = new DbRecentController(context);
            }
        }
        return mInstance ;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条联系人
     *
     * @param user
     */
    public void insertRecent(RecentBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentBeanDao userDao = daoSession.getRecentBeanDao();
        userDao.insert(user);
    }

    /**
     * 插入联系人集合
     *
     * @param users
     */
    public void insertRecentList(List<RecentBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentBeanDao userDao = daoSession.getRecentBeanDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteRecent(RecentBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentBeanDao userDao = daoSession.getRecentBeanDao();
        userDao.delete(user);
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateRecent(RecentBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentBeanDao userDao = daoSession.getRecentBeanDao();
        userDao.update(user);
    }

    /**
     * 查询联系人列表
     */
    public List<RecentBean> queryRecentList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentBeanDao userDao = daoSession.getRecentBeanDao();
        QueryBuilder<RecentBean> qb = userDao.queryBuilder();
        List<RecentBean> list = qb.list();
        return list;
    }

    /**
     * 查询维度下联系人集合列表
     */
    public List<RecentBean> queryRecentList(String department_id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentBeanDao userDao = daoSession.getRecentBeanDao();
        QueryBuilder<RecentBean> qb = userDao.queryBuilder();
        qb.where(RecentBeanDao.Properties.Department_id.gt(department_id))
                .orderAsc(RecentBeanDao.Properties.Department_id);
        List<RecentBean> list = qb.list();
        return list;
    }
}
