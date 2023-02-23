package com.ydh.intelligence.entitys;

import java.util.List;

/**
 * Date:2023/2/17
 * Time:15:28
 * author:ydh
 */
public class ImgEntity {

    /**
     * img : https://wenxin.baidu.com/younger/file/ERNIE-ViLG/2b5718c69aef65137945df4aac0ecf1cex
     * waiting : 0
     * imgUrls : [{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/2b5718c69aef65137945df4aac0ecf1cex","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/2b5718c69aef65137945df4aac0ecf1ci4","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/2b5718c69aef65137945df4aac0ecf1c5q","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/2b5718c69aef65137945df4aac0ecf1c30","score":null}]
     * createTime : 2022-08-19 17:07:02
     * requestId : 2883dd9e9dc54d6ee00894d7b37f022a
     * style : 油画
     * text : 睡莲
     * resolution : 1024 * 1024
     * taskId : 1037013
     * status : 1
     */

    private String img;
    private String waiting;
    private String createTime;
    private String requestId;
    private String style;
    private String text;
    private String resolution;
    private String taskId;
    private Integer status;//0或1。"1"表示已生成完成，"0"表示任务排队中或正在处理。
    private List<ImgUrlsEntity> imgUrls;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWaiting() {
        return waiting;
    }

    public void setWaiting(String waiting) {
        this.waiting = waiting;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ImgUrlsEntity> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsEntity> imgUrls) {
        this.imgUrls = imgUrls;
    }


}
