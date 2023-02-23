package com.ydh.intelligence.entitys;


import com.ydh.intelligence.db.ActionEntity;

import java.util.List;

/**
 * Created by ydh on 2022/9/23
 */
public class DataEntity {
    private String name;
    private boolean isCycle;
    private long createTime;
    private List<ActionEntity> child;


    public boolean isCycle() {
        return isCycle;
    }

    public void setCycle(boolean cycle) {
        isCycle = cycle;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ActionEntity> getChild() {
        return child;
    }

    public void setChild(List<ActionEntity> child) {
        this.child = child;
    }
}
