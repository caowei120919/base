package com.datacvg.sempmobile.baseandroid.room;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * FileName: DebugInfoDao
 * Author: 曹伟
 * Date: 2019/9/16 15:33
 * Description:
 */

@Dao
public interface DebugInfoDao {

    @Query("SELECT * FROM debuginfo")
    public Flowable<List<DebugInfo>> loadAll();

    //Using Room DB,Need to Use DataSource.Factory Implement PositionalDataSource
    @Query("SELECT * FROM debuginfo")
    public DataSource.Factory<Integer, DebugInfo> loadDataSource();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDebugInfo(List<DebugInfo> debugInfos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDebugInfo(DebugInfo debugInfo);

    @Query("DELETE FROM debuginfo")
    void deleteAllDebugInfo();

}
