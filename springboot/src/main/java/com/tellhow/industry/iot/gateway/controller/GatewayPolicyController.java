package com.tellhow.industry.iot.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiresPermissions("recognization:policy")
@RestController
@RequestMapping("/gateway/policy")
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
}
