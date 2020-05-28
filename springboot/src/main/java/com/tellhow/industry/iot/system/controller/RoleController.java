package com.tellhow.industry.iot.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.system.service.RoleService;
import com.tellhow.industry.iot.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiresPermissions("system:role")
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRoles")
    public JSONObject getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * 角色列表
     */
    @PostMapping("/findRole")
    public JSONObject findRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId");
        return roleService.findRole(requestJson);
    }

    /**
     * 角色列表
     */
    @GetMapping("/listRole")
    public JSONObject listRole() {
        return roleService.listRole();
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    @GetMapping("/listAllPermission")
    public JSONObject listAllPermission() {
        return roleService.listAllPermission();
    }

    /**
     * 新增角色
     */
    @PostMapping("/addRole")
    public JSONObject addRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleName,roleDescription");
        return roleService.addRole(requestJson);
    }

    /**
     * 修改角色
     */
    @PostMapping("/updateRole")
    public JSONObject updateRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId,roleName,roleDescription");
        return roleService.updateRole(requestJson);
    }

    @PostMapping("/updateRolePermission")
    public JSONObject updateRolePermission(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId,permissionIds");
        return roleService.updateRolePermission(requestJson);
    }

    /**
     * 修改角色
     */
    @PostMapping("/enableDisableRole")
    public JSONObject enableDisableRole(@RequestBody JSONObject requestJson) {
        System.out.println("enableDisableRole");
        CommonUtil.hasAllRequired(requestJson, "roleId,roleEnable");
        return roleService.enableDisableRole(requestJson);
    }

    /**
     * 删除角色
     */
    @PostMapping("/deleteRole")
    public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId");
        return roleService.deleteRole(requestJson);
    }
}
