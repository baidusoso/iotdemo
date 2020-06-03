package com.tellhow.industry.iot.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.model.AddGatewayPolicyRequest;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiresPermissions("recognization:policy")
@RestController
@RequestMapping("/policy")
public class GatewayPolicyController {
    @Autowired
    private GatewayPolicyService policyService;

    @PostMapping("/list")
    public JSONObject getGatewayPolicyList(@RequestBody JSONObject requestJson) {
        return policyService.getGatewayPolicyList(requestJson);
    }

    @GetMapping("/sync")
    public JSONObject syncGatewayPolicy() {
        return policyService.syncGatewayPolicy();
    }

    @PostMapping("/gateway")
    public JSONObject addGatewayPolicy(@RequestBody AddGatewayPolicyRequest addGatewayPolicyRequest) {
        return policyService.addGatewayPolicy(addGatewayPolicyRequest);
    }
}
