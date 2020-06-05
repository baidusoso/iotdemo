package com.tellhow.industry.iot.account.service;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.model.IAMAccount;

import java.util.List;

public interface AccountService {
    JSONObject getAccountList(JSONObject jsonObject);

    JSONObject syncAccount();

    JSONObject saveOrUpdateUser(List<IAMAccount> iamAccountList);

    JSONObject deleteUsers(List<String> deleteUserIds);
}
