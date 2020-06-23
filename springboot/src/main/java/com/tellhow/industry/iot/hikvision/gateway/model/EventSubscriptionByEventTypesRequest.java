package com.tellhow.industry.iot.hikvision.gateway.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 单个添加人脸请求参数
 */
public class EventSubscriptionByEventTypesRequest {
    /**
     * 事件类型
     */
    public List<Number> eventTypes=new ArrayList<>();
    /**
     * 指定事件接收的地址，采用restful回调模式，支持http和https
     */
    public String eventDest;

}
