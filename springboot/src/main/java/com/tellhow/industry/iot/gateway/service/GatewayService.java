package com.tellhow.industry.iot.gateway.service;

import com.alibaba.fastjson.JSONObject;

public interface GatewayService {
    JSONObject getGatewayList(JSONObject jsonObject);
    JSONObject getGatewayDoorListByGateway(JSONObject jsonObject);

    JSONObject syncGateway();
}
