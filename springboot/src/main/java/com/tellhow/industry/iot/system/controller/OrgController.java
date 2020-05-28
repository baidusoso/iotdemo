package com.tellhow.industry.iot.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.hikvision.org.OrgApi;
import com.tellhow.industry.iot.gateway.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.system.service.OrgService;
import com.tellhow.industry.iot.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiresPermissions("system:org")
@RestController
@RequestMapping("/system/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @GetMapping("/syncOrg")
    public JSONObject syncOrg() {
        return orgService.syncOrg();
    }

    @GetMapping("/getRootOrg")
    public JSONObject getRootOrg() {
        return orgService.getRootOrg();
    }

    @PostMapping("/addOrg")
    public JSONObject addOrg(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "orgName");
        return orgService.addOrg(requestJson);
    }

    @PostMapping("/updateOrg")
    public JSONObject updateOrg(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "orgName,orgCode");
        return orgService.updateOrg(requestJson);
    }

    @PostMapping("/deleteOrg")
    public JSONObject deleteOrg(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "orgCode");
        return orgService.deleteOrg(requestJson);
    }
}
