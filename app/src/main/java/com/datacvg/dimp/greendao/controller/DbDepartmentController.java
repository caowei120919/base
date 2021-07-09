package com.datacvg.dimp.greendao.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.datacvg.dimp.baseandroid.greendao.bean.DaoMaster;
import com.datacvg.dimp.baseandroid.greendao.bean.DaoSession;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.bean.DepartmentBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description : 部门基础的增删改查操作
 */
public class DbDepartmentController {
    private static DbDepartmentController mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private String dbName = "db_department" ;

    private DbDepartmentController(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbDepartmentController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbContactController.class) {
                if (mInstance == null) {
                    mInstance = new DbDepartmentController(context);
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
    public void insertOrUpdateDepartment(DepartmentBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        userDao.insertOrReplace(user);
    }

    /**
     * 插入联系人集合
     *
     * @param users
     */
    public void insertDepartmentList(List<DepartmentBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteDepartment(DepartmentBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        userDao.delete(user);
    }

    public void deleteAllDepartment(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        userDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateDepartment(DepartmentBean user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        userDao.update(user);
    }

    /**
     * 查询联系人列表
     */
    public List<DepartmentBean> queryDepartmentList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        QueryBuilder<DepartmentBean> qb = userDao.queryBuilder();
        List<DepartmentBean> list = qb.list();
        return list;
    }

    /**
     * 查询维度下联系人集合列表
     */
    public List<DepartmentBean> queryDepartmentList(int department_id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        QueryBuilder<DepartmentBean> qb = userDao.queryBuilder();
        qb.where(DepartmentBeanDao.Properties.Department_id.gt(department_id))
                .orderAsc(DepartmentBeanDao.Properties.Department_id);
        List<DepartmentBean> list = qb.list();
        return list;
    }

    /**
     * 根据父id查询部门
     * @param partentId
     * @return
     */
    public List<DepartmentBean> queryDepartmentListForParent(String partentId){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        QueryBuilder<DepartmentBean> qb = userDao.queryBuilder();
        qb.where(DepartmentBeanDao.Properties.D_res_parentid.eq(partentId));
        List<DepartmentBean> list = qb.list();
        return list;
    }

    /**
     * 根据部门id查询部门名称
     * @param department_pkId
     * @return
     */
    public String getDepartmentNameForDepartmentId(String department_pkId){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        QueryBuilder<DepartmentBean> qb = userDao.queryBuilder();
        qb.where(DepartmentBeanDao.Properties.D_res_pkid.eq(department_pkId));
        DepartmentBean departmentBean = qb.build().unique();
        return departmentBean.getD_res_clname() ;
    }


    public int getParentDepartmentIdForResId(String d_res_parentid) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DepartmentBeanDao userDao = daoSession.getDepartmentBeanDao();
        QueryBuilder<DepartmentBean> qb = userDao.queryBuilder();
        qb.where(DepartmentBeanDao.Properties.D_res_id.eq(d_res_parentid));
        List<DepartmentBean> list = qb.list();
        if(list.size() > 0){
         return list.get(0).getDepartment_id().intValue();
        }
        return 0;
    }
}
