package com.tellhow.industry.iot.system.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {
	/**
	 * 用户列表
	 */
	JSONObject listUser(JSONObject jsonObject);

	JSONObject getAllRolesByUser(JSONObject jsonObject);

	/**
	 * 添加用户
	 */
	JSONObject addUser(JSONObject jsonObject);

	/**
	 * 修改用户
	 */
	JSONObject updateUser(JSONObject jsonObject);
	JSONObject updateUserRole(JSONObject jsonObject);
	JSONObject deleteUser(JSONObject jsonObject);
}
