package com.tellhow.industry.iot.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.service.GatewayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiresPermissions("recognization:gateway")
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @PostMapping("/list")
    public JSONObject getGatewayList(@RequestBody JSONObject requestJson) {
        return gatewayService.getGatewayList(requestJson);
    }

    @GetMapping("/sync")
    public JSONObject syncGateway() {
        return gatewayService.syncGateway();
    }
}
