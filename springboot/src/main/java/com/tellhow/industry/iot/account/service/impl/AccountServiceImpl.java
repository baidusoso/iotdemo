package com.tellhow.industry.iot.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.account.service.AccountService;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.hikvision.GatewayException;
import com.tellhow.industry.iot.gateway.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;

    @Override
    public JSONObject getAccountList(JSONObject jsonObject) {
        List<JSONObject> roles = accountDao.getAccountList(jsonObject);
        return CommonUtil.successPage(roles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject syncAccount() {
        try {
            List<ElasticsearchApi.Account> accountList = ElasticsearchApi.getAccountList();
            accountDao.tempDeleteAllAccount();
            if (accountList != null && accountList.size() > 0) {
                for (ElasticsearchApi.Account account : accountList) {
                    if (account.delFlag != 0) continue;
                    accountDao.insertOrUpdateAccount(account);
                }
            }
        } catch (GatewayException gatewayException) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, gatewayException.getMessage());
        }
        return CommonUtil.successJson();
    }
}
