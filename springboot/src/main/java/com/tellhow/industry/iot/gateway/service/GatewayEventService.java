package com.tellhow.industry.iot.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.model.DoorEvent;

public interface GatewayEventService {
    JSONObject saveEvent(DoorEvent doorEvent);
    JSONObject getEventList(JSONObject jsonObject);
}
