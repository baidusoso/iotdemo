package com.tellhow.industry.iot.account.service;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.model.Guest;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    JSONObject getAccountList(JSONObject jsonObject);

    JSONObject syncAccount();

    JSONObject getAccountInfo(String userId);

    JSONObject getAccountInfoByCertificateNum(String certificateNum);

    JSONObject saveOrUpdateUser(List<ElasticsearchApi.Account> iamAccountList);

    JSONObject deleteUsers(List<String> deleteUserIds);

    JSONObject saveOrUpdateGuest(Guest guest);

    JSONObject getGuestList(JSONObject jsonObject);

    Guest getGuest(String id);

    JSONObject getGuestVisitHistory(String userId);

    JSONObject reviewGuest(String id, boolean result);
}
