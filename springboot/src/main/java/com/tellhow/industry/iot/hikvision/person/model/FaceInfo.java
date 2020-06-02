package com.tellhow.industry.iot.hikvision.person.model;

/**
 * 人脸信息对象
 */
public class FaceInfo {
    /**
     * 人脸的名称
     */
    public String name;//必须
    /**
     * 人脸的性别信息
     */
    public String sex;
    /**
     * 人脸的证件类别信息
     */
    public String certificateType;
    /**
     * 人脸的证件号码信息。
     */
    public String certificateNum;
}
