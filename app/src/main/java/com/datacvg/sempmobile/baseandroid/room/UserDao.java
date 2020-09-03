package com.datacvg.sempmobile.baseandroid.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * FileName: UserDao
 * Author: 曹伟
 * Date: 2019/9/16 15:32
 * Description:
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert
    void insertUsers(User... users);

    @Insert
    public void insertUsers(List<User> userList);

    @Update
    public void updateUser(User user);

    @Update
    public void updateUsers(User... users);

    @Update
    public void updateUsers(List<User> userList);

    @Delete
    void delete(User user);

    @Delete
    public void deleteUsers(User... users);

    @Delete
    public void deleteUsers(List<User> userList);

    @Query("DELETE FROM Users")
    void deleteAllUsers();

    @Query("SELECT * FROM Users")
    List<User> getAll();

    @Query("SELECT * FROM Users")
    public Flowable<List<User>> loadAll();

    @Query("SELECT * FROM Users LIMIT 1")
    User getUser();

    // 对于记录不存在,会返回空的列表
    @Query("SELECT * FROM Users WHERE userid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Users WHERE username LIKE :username AND " + "last_name LIKE :last LIMIT 1")
    User findByName(String username, String last);

    //对于数据可能不存在的场景,使用Maybe更符合语义,如果记录不存在,则该Maybe会直接结束onComplete()
    @Query("SELECT * FROM Users WHERE username LIKE :username AND " + "last_name LIKE :last LIMIT 1")
    Maybe<User> findByNameMaybeEmpty(String username, String last);

    @Query("SELECT * from Users where userid = :id LIMIT 1")
    public Flowable<User> loadUserById(int id);

}
