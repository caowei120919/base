package com.datacvg.sempmobile.baseandroid.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * FileName: ThreadInfoDao
 * Author: 曹伟
 * Date: 2019/9/16 15:21
 * Description:
 */

@Dao
public interface ThreadInfoDao {

    @Query("SELECT * FROM threadinfo WHERE tag = :tag")
    public List<ThreadInfo> loadAll(String tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertThreadInfo(ThreadInfo threadInfo);

    @Query("DELETE FROM threadinfo WHERE tag = :tag")
    void deleteThreadInfo(String tag);

}
