package com.tellhow.industry.iot.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.model.AddGatewayPolicyRequest;
import com.tellhow.industry.iot.gateway.model.DeleteGatewayPolicyRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GatewayPolicyService {
    JSONObject getGatewayPolicyList(JSONObject jsonObject);

    JSONObject syncGatewayPolicy();

    JSONObject addGatewayPolicy(AddGatewayPolicyRequest addGatewayPolicyRequest);

    JSONObject deleteGatewayPolicy(@RequestBody DeleteGatewayPolicyRequest deleteGatewayPolicyRequest);

    JSONObject addOrDeleteGatewayPolicy(List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList, boolean delete);
}
