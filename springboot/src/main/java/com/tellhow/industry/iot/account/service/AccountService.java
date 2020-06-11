package com.tellhow.industry.iot.account.service;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.List;

public interface AccountService {
    JSONObject getAccountList(JSONObject jsonObject);

    JSONObject syncAccount();

    JSONObject getAccountInfo(String userId);

    JSONObject saveOrUpdateUser(List<ElasticsearchApi.Account> iamAccountList);

    JSONObject deleteUsers(List<String> deleteUserIds);
}
