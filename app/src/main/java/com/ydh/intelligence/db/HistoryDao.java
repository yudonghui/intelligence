package com.ydh.intelligence.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by ydh on 2022/9/21
 */
@Dao
public interface HistoryDao {
    @Query("SELECT * FROM historyEntity  ORDER BY createTime ASC")
    List<HistoryEntity> queryAll();

    /**
     * 用过创建时间获取所有的事件，并按照操作时间排序
     */
    @Query("SELECT * FROM historyEntity WHERE createTime = :createTime ORDER BY createTime ASC")
    List<HistoryEntity> queryByTime(Long createTime);

    @Insert
    void insert(HistoryEntity historyEntity);

    @Insert
    void insert(HistoryEntity... historyEntitys);

    @Insert
    void insert(List<HistoryEntity> historyEntitys);

    @Update
    void update(HistoryEntity historyEntity);

    @Query("DELETE  FROM historyEntity WHERE createTime=:createTime")
    void delete(Long createTime);
}
