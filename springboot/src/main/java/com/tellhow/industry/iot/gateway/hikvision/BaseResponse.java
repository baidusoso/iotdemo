package com.tellhow.industry.iot.gateway.hikvision;

public class BaseResponse<T> {
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
