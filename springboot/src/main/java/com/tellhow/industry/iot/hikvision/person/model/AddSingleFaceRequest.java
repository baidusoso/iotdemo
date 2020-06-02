package com.tellhow.industry.iot.hikvision.person.model;

/**
 * 单个添加人脸请求参数
 */
public class AddSingleFaceRequest {
    /**
     * 指定人脸添加到的人脸分组的唯一标识
     */
    public String faceGroupIndexCode;//必须
    /**
     * 人脸信息对象
     */
    public FaceInfo faceInfo;//必须
    /**
     * 人脸图片对象
     */
    public FacePic facePic;//必须
}
