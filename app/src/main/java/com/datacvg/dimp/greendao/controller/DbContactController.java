package com.datacvg.dimp.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.datacvg.dimp.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.dimp.baseandroid.greendao.bean.DaoSession;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.bean.ContactBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description : 联系人数据库基础的增删改查操作
 */
public class DbContactController {
    private static DbContactController mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private String dbName = "db_contact" ;

    private DbContactController(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbContactController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbContactController.class) {
                if (mInstance == null) {
                    mInstance = new DbContactController(context);
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
    public void insertOrUpdateContact(ContactBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactBeanDao userDao = daoSession.getContactBeanDao();
        userDao.insertOrReplace(user);
    }

    /**
     * 插入联系人集合
     *
     * @param users
     */
    public void insertContactList(List<ContactBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactBeanDao userDao = daoSession.getContactBeanDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteContact(ContactBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactBeanDao userDao = daoSession.getContactBeanDao();
        userDao.delete(user);
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateContact(ContactBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactBeanDao userDao = daoSession.getContactBeanDao();
        userDao.update(user);
    }

    /**
     * 查询联系人列表
     */
    public List<ContactBean> queryContactList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactBeanDao userDao = daoSession.getContactBeanDao();
        QueryBuilder<ContactBean> qb = userDao.queryBuilder();
        List<ContactBean> list = qb.list();
        return list;
    }

    /**
     * 查询维度下联系人集合列表
     */
    public List<ContactBean> queryContactList(String department_id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactBeanDao userDao = daoSession.getContactBeanDao();
        QueryBuilder<ContactBean> qb = userDao.queryBuilder();
        qb.where(ContactBeanDao.Properties.Department_id.eq(department_id))
                .orderAsc(ContactBeanDao.Properties.Department_id);
        List<ContactBean> list = qb.list();
        return list;
    }
}
