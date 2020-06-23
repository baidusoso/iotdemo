package com.tellhow.industry.iot.gateway.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.model.AuditLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GatewayEventDao {

    void saveAuditLog(@Param("event") AuditLog auditLog);

    int getEventCount(JSONObject jsonObject);

    List<JSONObject> getEventList(JSONObject jsonObject);
}
