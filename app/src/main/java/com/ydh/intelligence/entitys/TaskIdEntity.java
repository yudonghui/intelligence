package com.ydh.intelligence.entitys;

/**
 * Date:2023/2/17
 * Time:16:20
 * author:ydh
 */
public class TaskIdEntity {
    private String requestId;
    private Long taskId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
