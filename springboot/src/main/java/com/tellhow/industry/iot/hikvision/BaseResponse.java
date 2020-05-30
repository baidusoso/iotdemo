package com.tellhow.industry.iot.hikvision;

public class BaseResponse<T> {

    public static final String ERR_NO_DATA = "海康安防管理平台返回空数据";
    /**
     * 返回码
     */
    public String code;
    /**
     * 返回描述
     */
    public String msg;
    /**
     * 接口返回结果，根据接口定义
     */
    public T data;
}
