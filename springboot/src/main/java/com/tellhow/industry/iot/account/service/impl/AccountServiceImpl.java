package com.tellhow.industry.iot.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.account.service.AccountService;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.system.dao.OrgDao;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import com.tellhow.industry.iot.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;

    @Autowired
    OrgDao orgDao;

    @Override
    public JSONObject getAccountList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = accountDao.getAccountCount(jsonObject);
        List<JSONObject> accountList = accountDao.getAccountList(jsonObject);
        return CommonUtil.successPage(jsonObject, accountList, count);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject saveOrUpdateUser(List<IAMAccount> iamAccountList) {
        if (iamAccountList == null || iamAccountList.size() == 0) {
            return CommonUtil.errorJson(Constants.ERROR_400, "请求体为空");
        }
        return CommonUtil.successJson();
    }
}
