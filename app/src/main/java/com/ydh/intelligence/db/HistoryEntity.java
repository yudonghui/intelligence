package com.ydh.intelligence.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Date:2023/2/17
 * Time:16:53
 * author:ydh
 */
@Entity
public class HistoryEntity {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private long id;
    private String text;//搜索内容
    private String style;//风格
    private String resolution;//分辨率
    private String taskId;//任务
    private String createTime;//搜索时间
    private Integer num;
    private String obj1;//
    private String obj2;//

    @Ignore
    public HistoryEntity(String text, String style, String resolution, String taskId, String createTime) {
        this.text = text;
        this.style = style;
        this.resolution = resolution;
        this.taskId = taskId;
        this.createTime = createTime;
    }

    @Ignore
    public HistoryEntity(String text, String style, String resolution, String taskId, String createTime, Integer num) {
        this.text = text;
        this.style = style;
        this.resolution = resolution;
        this.taskId = taskId;
        this.createTime = createTime;
        this.num = num;
    }

    public HistoryEntity() {
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getObj1() {
        return obj1;
    }

    public void setObj1(String obj1) {
        this.obj1 = obj1;
    }

    public String getObj2() {
        return obj2;
    }

    public void setObj2(String obj2) {
        this.obj2 = obj2;
    }
}
