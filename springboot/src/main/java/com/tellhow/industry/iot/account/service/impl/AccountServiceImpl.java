package com.tellhow.industry.iot.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.account.service.AccountService;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.system.dao.OrgDao;
import com.tellhow.industry.iot.system.model.Org;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        for (IAMAccount account : iamAccountList) {
            if (StringUtils.isEmpty(account.id)) {
                return CommonUtil.errorJson(Constants.ERROR_400, "用户人员编码不能为空");
            }
            if (StringUtils.isEmpty(account.loginName)) {
                return CommonUtil.errorJson(Constants.ERROR_400, "用户登录名不能为空");
            }
            if (StringUtils.isEmpty(account.name)) {
                return CommonUtil.errorJson(Constants.ERROR_400, "用户姓名不能为空");
            }
            if (StringUtils.isEmpty(account.orgId)) {
                return CommonUtil.errorJson(Constants.ERROR_400, "用户归属部门不能为空");
            }
            Org org = orgDao.findOrgById(account.orgId);
            if (org == null) {
                return CommonUtil.errorJson(Constants.ERROR_400, account.name + "用户归属部门不存在");
            }
            accountDao.insertOrUpdateAccount(new ElasticsearchApi.Account(account));
            //TODO 是否需要添加到海康
        }
        return CommonUtil.successJson();
    }
}
