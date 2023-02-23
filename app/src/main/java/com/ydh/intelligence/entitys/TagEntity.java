package com.ydh.intelligence.entitys;

/**
 * Date:2023/2/17
 * Time:10:36
 * author:ydh
 */
public class TagEntity {
    private boolean isCheck;
    private String name;
    private String value;

    public TagEntity() {
    }

    public TagEntity(String name) {
        this.name = name;
    }

    public TagEntity(boolean isCheck, String name) {
        this.isCheck = isCheck;
        this.name = name;
    }

    public TagEntity(boolean isCheck, String name, String value) {
        this.isCheck = isCheck;
        this.name = name;
        this.value = value;
    }

    public TagEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
