package com.tellhow.industry.iot.gateway.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.hikvision.gateway.model.Gateway;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GatewayDao {

    int getGatewayCount(JSONObject jsonObject);

    List<JSONObject> getGatewayList(JSONObject jsonObject);

    List<JSONObject> getGatewayDoorList(JSONObject jsonObject);

    List<Gateway.Door> getAllGatewayDoors();

    List<Gateway.Door> getGatewayDoorsMatchName(@Param("name") String name);

    int tempDeleteAllGateway();

    int insertOrUpdateGateway(@Param("gateway") Gateway gateway);

    int tempDeleteAllGatewayDoor();

    int insertOrUpdateGatewayDoor(@Param("door") Gateway.Door door);

    Gateway.Door getGatewayDoorById(@Param("doorCode") String doorCode);
}
