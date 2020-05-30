package com.tellhow.industry.iot.account.service;

import com.alibaba.fastjson.JSONObject;

public interface AccountService {
    JSONObject getAccountList(JSONObject jsonObject);

    JSONObject syncAccount();
}
