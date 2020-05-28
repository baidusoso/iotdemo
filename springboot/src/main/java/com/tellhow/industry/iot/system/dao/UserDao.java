package com.tellhow.industry.iot.system.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: hxy
 * @description: 用户/角色/权限
 * @date: 2017-11-14 15:08:45
 */
public interface UserDao {
    /**
     * 查询用户数量
     */
    int countUser(JSONObject jsonObject);

    /**
     * 查询用户列表
     */
    List<JSONObject> listUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    List<JSONObject> getAllRolesByUser(JSONObject jsonObject);
    List<Integer> getRolesByUser(JSONObject jsonObject);

    /**
     * 校验用户名是否已存在
     */
    int queryExistUsername(JSONObject jsonObject);

    /**
     * 新增用户
     */
    int addUser(JSONObject jsonObject);

    /**
     * 修改用户
     */
    int updateUser(JSONObject jsonObject);
    int deleteUser(JSONObject jsonObject);

    /**
     * 批量插入角色的权限
     *
     * @param userId      用户ID
     * @param roles 角色
     */
    int insertUserRole(@Param("userId") String userId, @Param("roles") List<Integer> roles);

    int removeOldUserRole(@Param("userId") String userId, @Param("roles") List<Integer> roles);

}
