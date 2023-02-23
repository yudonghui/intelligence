package com.ydh.intelligence.entitys;

import java.io.Serializable;

/**
 * Date:2023/2/17
 * Time:15:45
 * author:ydh
 */
public class ImgUrlsEntity implements Serializable {
    private String image;
    private Object score;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }
}
