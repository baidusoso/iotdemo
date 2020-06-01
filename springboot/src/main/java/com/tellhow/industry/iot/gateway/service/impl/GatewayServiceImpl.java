package com.tellhow.industry.iot.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.dao.GatewayDao;
import com.tellhow.industry.iot.gateway.service.GatewayService;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.gateway.GatewayApi;
import com.tellhow.industry.iot.hikvision.gateway.model.Gateway;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GatewayServiceImpl implements GatewayService {
    @Autowired
    GatewayDao gatewayDao;

    @Override
    public JSONObject getGatewayList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = gatewayDao.getGatewayCount(jsonObject);
        List<JSONObject> gatewayList = gatewayDao.getGatewayList(jsonObject);
        return CommonUtil.successPage(jsonObject, gatewayList, count);
    }

    @Override
    public JSONObject getGatewayDoorListByGateway(JSONObject jsonObject) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject syncGateway() {
        try {
            List<Gateway.Door> doorList = new GatewayApi().getGatewayDoorList();
            gatewayDao.tempDeleteAllGatewayDoor();
            if (doorList != null && doorList.size() > 0) {
                for (Gateway.Door door : doorList) {
                    gatewayDao.insertOrUpdateGatewayDoor(door);
                }
            }
        } catch (GatewayException gatewayException) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, gatewayException.getMessage());
        }
        return CommonUtil.successJson();
    }
}
