package com.tellhow.industry.iot.gateway.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GatewayPolicyDao {
    int getGatewayPolicyCount(JSONObject jsonObject);

    List<JSONObject> getGatewayPolicyList(JSONObject jsonObject);

    int tempDeleteAllGatewayPolicy();

    int insertOrUpdateGatewayPolicy(@Param("policy") ElasticsearchApi.GatewayPolicy policy);
}
