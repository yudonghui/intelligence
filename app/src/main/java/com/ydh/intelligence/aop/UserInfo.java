package com.ydh.intelligence.aop;

/**
 * Date:2023/3/14
 * Time:13:46
 * author:ydh
 */
public class UserInfo extends BaseUse {
    private String name;
    protected int age;
    public String sex;

    public UserInfo() {

    }

    public UserInfo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
