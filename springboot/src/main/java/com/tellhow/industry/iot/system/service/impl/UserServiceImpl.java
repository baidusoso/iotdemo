package com.tellhow.industry.iot.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.config.shiro.UserRealm;
import com.tellhow.industry.iot.system.dao.UserDao;
import com.tellhow.industry.iot.system.service.UserService;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: hxy
 * @description: 用户/角色/权限
 * @date: 2017/11/2 10:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户列表
     */
    @Override
    public JSONObject listUser(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = userDao.countUser(jsonObject);
        List<JSONObject> list = userDao.listUser(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 添加用户
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject addUser(JSONObject jsonObject) {
        int exist = userDao.queryExistUsername(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        userDao.addUser(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getAllRolesByUser(JSONObject jsonObject) {
        List<JSONObject> roles = userDao.getAllRolesByUser(jsonObject);
        return CommonUtil.successPage(roles);
    }

    /**
     * 修改用户
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject updateUser(JSONObject jsonObject) {
        userDao.updateUser(jsonObject);
        return CommonUtil.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject deleteUser(JSONObject jsonObject) {
        userDao.deleteUser(jsonObject);
        return CommonUtil.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject updateUserRole(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        List<Integer> newRoleIds = (List<Integer>) jsonObject.get("roleIds");
        List<Integer> oldRoleIds = userDao.getRolesByUser(jsonObject);
        //添加新权限
        saveNewRole(userId, newRoleIds, oldRoleIds);
        //移除旧的不再拥有的权限
        removeOldRole(userId, newRoleIds, oldRoleIds);
        return CommonUtil.successJson();
    }

    /**
     * 为角色添加新权限
     */
    private void saveNewRole(String userId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }
        if (waitInsert.size() > 0) {
            userDao.insertUserRole(userId, waitInsert);
        }
    }

    /**
     * 删除角色 旧的 不再拥有的权限
     */
    private void removeOldRole(String userId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            userDao.removeOldUserRole(userId, waitRemove);
        }
    }
}
