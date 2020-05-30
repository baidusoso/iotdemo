package com.tellhow.industry.iot.gateway.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.hikvision.gateway.model.Gateway;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GatewayDao {

    List<JSONObject> getGatewayList(JSONObject jsonObject);

    List<JSONObject> getGatewayDoorList(JSONObject jsonObject);

    int tempDeleteAllGateway();

    int insertOrUpdateGateway(@Param("gateway") Gateway gateway);

    int tempDeleteAllGatewayDoor();

    int insertOrUpdateGatewayDoor(@Param("door") Gateway.Door door);
}
