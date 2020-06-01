package com.tellhow.industry.iot.hikvision.gateway.model;

public class AddAuthDownloadTaskRequest {

    public static final int TASK_TYPE_CARD = 1;
    public static final int TASK_TYPE_FACE = 4;
    /**
     * 下载任务类型
     * 1：卡片
     * 4：人脸
     */
    public int taskType;

    public AddAuthDownloadTaskRequest(int taskType) {
        this.taskType = taskType;
    }
}
