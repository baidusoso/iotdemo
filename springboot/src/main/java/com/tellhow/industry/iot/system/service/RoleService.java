package com.tellhow.industry.iot.system.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: hxy
 * @date: 2017/10/30 13:10
 */
public interface RoleService {
	/**
	 * 查询某用户的 角色  菜单列表   权限列表
	 */
	JSONObject getUserPermission(String username);

	/**
	 * 查询所有的角色
	 * 在添加/修改用户的时候要使用此方法
	 */
	JSONObject getAllRoles();

	JSONObject findRole(JSONObject jsonObject);
	/**
	 * 角色列表
	 */
	JSONObject listRole();

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	JSONObject listAllPermission();

	/**
	 * 添加角色
	 */
	JSONObject addRole(JSONObject jsonObject);

	/**
	 * 修改角色
	 */
	JSONObject updateRole(JSONObject jsonObject);
	JSONObject updateRolePermission(JSONObject jsonObject);

	/**
	 * 修改角色
	 */
	JSONObject enableDisableRole(JSONObject jsonObject);

	/**
	 * 删除角色
	 */
	JSONObject deleteRole(JSONObject jsonObject);
}
