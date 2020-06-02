package com.tellhow.industry.iot.hikvision.person.model;

/**
 * 单个添加人脸返回接口
 */
public class AddSingleFaceResponse {
    /**
     * 人脸的唯一标识
     */
    public String indexCode;
    /**
     * 人脸所属的人脸分组的唯一标识
     */
    public String faceGroupIndexCode;

    /**
     * 人脸信息对象
     */
    public FaceInfo faceInfo;//必须
    /**
     * 人脸图片对象
     */
    public FacePic facePic;//必须
}
