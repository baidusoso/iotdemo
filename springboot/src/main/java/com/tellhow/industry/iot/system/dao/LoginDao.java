package com.tellhow.industry.iot.system.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

public interface LoginDao {
    /**
     * 根据用户名和密码查询对应的用户
     */
    JSONObject getUser(@Param("username") String username, @Param("password") String password);
}
