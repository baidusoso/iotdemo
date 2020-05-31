package com.tellhow.industry.iot.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.dao.GatewayPolicyDao;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayPolicyServiceImpl implements GatewayPolicyService {

    @Autowired
    GatewayPolicyDao policyDao;

    @Override
    public JSONObject getGatewayPolicyList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = policyDao.getGatewayPolicyCount(jsonObject);
        List<JSONObject> gatewayPolicyList = policyDao.getGatewayPolicyList(jsonObject);
        return CommonUtil.successPage(jsonObject, gatewayPolicyList, count);
    }

    @Override
    public JSONObject syncGatewayPolicy() {
        try {
            List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList = ElasticsearchApi.getGatewayPolicyList();
            policyDao.tempDeleteAllGatewayPolicy();
            if (gatewayPolicyList != null && gatewayPolicyList.size() > 0) {
                for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                    policyDao.insertOrUpdateGatewayPolicy(gatewayPolicy);
                }
            }
        } catch (GatewayException gatewayException) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, gatewayException.getMessage());
        }
        return CommonUtil.successJson();
    }
}
