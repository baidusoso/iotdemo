package com.tellhow.industry.iot.account.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.model.Guest;
import com.tellhow.industry.iot.account.model.IAMAccount;
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

    int batchDeleteAccountByIds(@Param("ids") List<String> ids);

    ElasticsearchApi.Account getAccountById(@Param("id") String id);

    ElasticsearchApi.Account getAccountByCertificateNum(@Param("certificateNum") String certificateNum);

    int updateFaceId(@Param("id") String id, @Param("faceId") String faceId);

    int insertOrUpdateGuest(@Param("guest") Guest guest);

    int getGuestCount(JSONObject jsonObject);

    List<JSONObject> getGuestList(JSONObject jsonObject);
}
