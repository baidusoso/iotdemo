package com.tellhow.industry.iot.gateway.model;

import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.List;

public class AddGatewayPolicyRequest {
    public List<ElasticsearchApi.GatewayPolicy> policies;
}
