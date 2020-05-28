package com.tellhow.industry.iot.system.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author: hxy
 * @date: 2017/10/30 13:28
 */
public interface RoleDao {
	/**
	 * 查询用户的角色 菜单 权限
	 */
	JSONObject getUserPermission(String username);

	/**
	 * 查询所有的菜单
	 */
	Set<String> getAllMenu();

	/**
	 * 查询所有的权限
	 */
	Set<String> getAllPermission();

	/**
	 * 角色列表
	 */
	List<JSONObject> listRole();
	List<JSONObject> getAllRoles();

	JSONObject findRole(JSONObject jsonObject);

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	List<JSONObject> listAllPermission();

	/**
	 * 新增角色
	 */
	int insertRole(JSONObject jsonObject);

	/**
	 * 批量插入角色的权限
	 *
	 * @param roleId      角色ID
	 * @param permissions 权限
	 */
	int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

	/**
	 * 将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
	 */
	int removeOldPermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

	/**
	 * 修改角色名称
	 */
	int updateRoleName(JSONObject jsonObject);

	/**
	 * 删除角色
	 */
	int enableDisableRole(JSONObject jsonObject);

	/**
	 * 查询某角色的全部数据
	 * 在删除和修改角色时调用
	 */
	JSONObject getRoleAllInfo(JSONObject jsonObject);

	/**
	 * 删除角色
	 */
	int removeRole(JSONObject jsonObject);

	/**
	 * 删除本角色全部权限
	 */
	int removeRoleAllPermission(JSONObject jsonObject);
}
