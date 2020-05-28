package com.tellhow.industry.iot.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.system.service.UserService;
import com.tellhow.industry.iot.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiresPermissions("system:user")
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public JSONObject listUser(HttpServletRequest request) {
        return userService.listUser(CommonUtil.request2Json(request));
    }

    @PostMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username, password, nickname,orgCode,phone");
        return userService.addUser(requestJson);
    }

    @PostMapping("/updateUser")
    public JSONObject updateUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, " username,nickname,orgCode, status, userId,phone");
        return userService.updateUser(requestJson);
    }

    @PostMapping("/getAllRolesByUser")
    public JSONObject getAllRolesByUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return userService.getAllRolesByUser(requestJson);
    }

    @PostMapping("/updateUserRole")
    public JSONObject updateUserRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId,roleIds");
        return userService.updateUserRole(requestJson);
    }

    @PostMapping("/deleteUser")
    public JSONObject deleteUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return userService.deleteUser(requestJson);
    }
}
