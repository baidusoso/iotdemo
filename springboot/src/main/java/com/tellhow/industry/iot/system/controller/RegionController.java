package com.tellhow.industry.iot.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.system.service.OrgService;
import com.tellhow.industry.iot.system.service.RegionService;
import com.tellhow.industry.iot.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiresPermissions("system:region")
@RestController
@RequestMapping("/system/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/syncRegion")
    public JSONObject syncRegion() {
        return regionService.syncRegion();
    }

    @GetMapping("/getRootRegion")
    public JSONObject getRootRegion() {
        return regionService.getRootRegion();
    }
}
