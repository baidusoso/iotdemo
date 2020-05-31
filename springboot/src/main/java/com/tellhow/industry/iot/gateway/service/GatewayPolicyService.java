package com.tellhow.industry.iot.gateway.service;

import com.alibaba.fastjson.JSONObject;

public interface GatewayPolicyService {
    JSONObject getGatewayPolicyList(JSONObject jsonObject);
    JSONObject syncGatewayPolicy();
}
