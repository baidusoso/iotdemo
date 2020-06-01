package com.tellhow.industry.iot.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.List;

public interface GatewayPolicyService {
    JSONObject getGatewayPolicyList(JSONObject jsonObject);

    JSONObject syncGatewayPolicy();

    JSONObject addGatewayPolicy(List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList);
}
