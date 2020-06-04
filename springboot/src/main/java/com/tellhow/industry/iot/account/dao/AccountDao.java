package com.tellhow.industry.iot.account.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountDao {

    /**
     * 查询用户数量
     */
    int getAccountCount(JSONObject jsonObject);

    List<JSONObject> getAccountList(JSONObject jsonObject);
    List<ElasticsearchApi.Account> getAllAccount();

    int tempDeleteAllAccount();

    int insertOrUpdateAccount(@Param("account") ElasticsearchApi.Account account);

    ElasticsearchApi.Account getAccountById(@Param("id") String id);

    int updateFaceId(@Param("id") String id, @Param("faceId") String faceId);
}
