package com.tellhow.industry.iot.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.model.DoorEvent;
import com.tellhow.industry.iot.gateway.service.GatewayEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequiresPermissions("face:policy")
@RestController
@RequestMapping("/event")
public class GatewayEventController {
    @Autowired
    private GatewayEventService gatewayEventService;

    @PostMapping("/door/face")
    public void onDoorEvent(@RequestBody DoorEvent doorEvent) {
        gatewayEventService.saveEvent(doorEvent);
    }

    @PostMapping("/list")
    public JSONObject getGatewayList(@RequestBody JSONObject requestJson) {
        return gatewayEventService.getEventList(requestJson);
    }
}
