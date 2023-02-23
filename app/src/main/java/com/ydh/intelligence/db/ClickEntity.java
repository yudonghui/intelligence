package com.ydh.intelligence.db;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by ydh on 2022/9/21
 */
@Entity
public class ClickEntity {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private long id;
    private String uuid;//设备id
    private long createTime;//创建日期
    private String inputContent;//搜索内容
    private String resultContent;//搜索结果
    private long useTokens;//用了多少token


    public ClickEntity() {
    }

    @Ignore
    public ClickEntity(long createTime) {
        this.createTime = createTime;
    }

    @Ignore
    public ClickEntity(String uuid, long createTime, String inputContent) {
        this.uuid = uuid;
        this.createTime = createTime;
        this.inputContent = inputContent;
    }

    @Ignore
    public ClickEntity(String uuid, long createTime, String inputContent, String resultContent, long useTokens) {
        this.uuid = uuid;
        this.createTime = createTime;
        this.inputContent = inputContent;
        this.resultContent = resultContent;
        this.useTokens = useTokens;
    }

    public long getUseTokens() {
        return useTokens;
    }

    public void setUseTokens(long useTokens) {
        this.useTokens = useTokens;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }
}
