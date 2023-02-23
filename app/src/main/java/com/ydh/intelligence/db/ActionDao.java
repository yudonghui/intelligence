package com.ydh.intelligence.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Date:2023/2/22
 * Time:15:43
 * author:ydh
 */
@Dao
public interface ActionDao {
    @Query("SELECT * FROM actionentity  ORDER BY actionTime ASC")
    List<ActionEntity> queryAll();

    /**
     * 用过创建时间获取所有的事件，并按照操作时间排序
     */
    @Query("SELECT * FROM actionentity WHERE createTime = :createTime ORDER BY actionTime ASC")
    List<ActionEntity> queryByTime(Long createTime);

    @Insert
    void insert(ActionEntity actionentity);

    @Insert
    void insert(ActionEntity... actionentitys);

    @Insert
    void insert(List<ActionEntity> actionentitys);

    @Update
    void update(ActionEntity actionentity);

    @Query("DELETE  FROM actionentity WHERE createTime=:createTime")
    void delete(Long createTime);
}
