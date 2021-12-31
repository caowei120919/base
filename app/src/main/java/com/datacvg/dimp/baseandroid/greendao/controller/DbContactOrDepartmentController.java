package com.datacvg.dimp.baseandroid.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBeanDao;
import com.datacvg.dimp.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.dimp.baseandroid.greendao.bean.DaoSession;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfoDao;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.bean.DepartmentBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-06
 * @Description :
 */
public class DbContactOrDepartmentController {
    private static DbContactOrDepartmentController mInstance ;
    private DaoMaster.DevOpenHelper openHelper ;
    private Context mContext ;
    private String dbName = "de_contact_department" ;

    private DbContactOrDepartmentController(Context context) {
        this.mContext = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbContactOrDepartmentController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbContactOrDepartmentController.class) {
                if (mInstance == null) {
                    mInstance = new DbContactOrDepartmentController(context);
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
    public void insertContactOrDepartment(ContactOrDepartmentBean debugInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao debugInfoDao = daoSession.getContactOrDepartmentBeanDao();
        debugInfoDao.insert(debugInfo);
    }

    /**
     * 全部删除
     */
    public void deleteAllResources(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        userDao.deleteAll();
    }

    public List<ContactOrDepartmentBean> queryChildResources(String parentId){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        qb.where(ContactOrDepartmentBeanDao.Properties.ResId.eq(parentId));
        List<ContactOrDepartmentBean> list = qb.list();
        return list;
    }

    /**
     * 查询所有资源
     * @return
     */
    public List<ContactOrDepartmentBean> queryAllResources(){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        List<ContactOrDepartmentBean> list = qb.list();
        return list;
    }

    public List<ContactOrDepartmentBean> queryDepartmentList(){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        qb.where(ContactOrDepartmentBeanDao.Properties.IsContact.eq(false));
        List<ContactOrDepartmentBean> list = qb.list();
        return list;
    }

    /**
     * 根据父id查询
     * @param partentId
     * @return
     */
    public List<ContactOrDepartmentBean> queryDepartmentListForParent(String partentId){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        qb.whereOr(ContactOrDepartmentBeanDao.Properties.ParentId.eq(partentId),ContactOrDepartmentBeanDao.Properties.IsContact.eq(false));
        List<ContactOrDepartmentBean> list = qb.list();
        return list;
    }

    public List<ContactOrDepartmentBean> queryContactList(String parentId){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        qb.whereOr(ContactOrDepartmentBeanDao.Properties.ParentId.eq(parentId),ContactOrDepartmentBeanDao.Properties.IsContact.eq(true));
        List<ContactOrDepartmentBean> list = qb.list();
        return list;
    }

    /**
     * 查找联系人
     * @return
     */
    public List<ContactOrDepartmentBean> queryContactList(){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        qb.where(ContactOrDepartmentBeanDao.Properties.IsContact.eq(true));
        List<ContactOrDepartmentBean> list = qb.list();
        return list;
    }

    public ContactOrDepartmentBean queryContact(String id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ContactOrDepartmentBeanDao userDao = daoSession.getContactOrDepartmentBeanDao();
        QueryBuilder<ContactOrDepartmentBean> qb = userDao.queryBuilder();
        qb.where(ContactOrDepartmentBeanDao.Properties.UserId.eq(id));
        ContactOrDepartmentBean contactOrDepartmentBean = qb.build().unique();
        return contactOrDepartmentBean ;
    }
}
