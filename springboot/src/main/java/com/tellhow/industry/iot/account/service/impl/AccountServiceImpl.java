package com.tellhow.industry.iot.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.account.service.AccountService;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.dao.GatewayDao;
import com.tellhow.industry.iot.gateway.dao.GatewayPolicyDao;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.org.OrgApi;
import com.tellhow.industry.iot.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.hikvision.person.PersonApi;
import com.tellhow.industry.iot.hikvision.person.model.BatchAddPersonResponse;
import com.tellhow.industry.iot.hikvision.person.model.Person;
import com.tellhow.industry.iot.system.dao.OrgDao;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountDao accountDao;

    @Autowired
    OrgDao orgDao;

    @Autowired
    GatewayPolicyDao policyDao;

    @Autowired
    GatewayDao gatewayDao;

    @Autowired
    GatewayPolicyService gatewayPolicyService;

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

    @Override
    public JSONObject getAccountInfo(String accountId) {
        ElasticsearchApi.Account account = accountDao.getAccountById(accountId);
        return CommonUtil.successJson(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject saveOrUpdateUser(List<ElasticsearchApi.Account> iamAccountList) {
        logger.debug("saveOrUpdateUser");
        if (iamAccountList == null || iamAccountList.size() == 0) {
            return CommonUtil.errorJson(Constants.ERROR_400, "请求体为空");
        }
        if (iamAccountList.size() > 1000) {
            return CommonUtil.errorJson(Constants.ERROR_400, "单次批量添加或者更新数量超过1000");
        }
        logger.debug("获取根组织");
        OrgApi orgApi = new OrgApi();
        OrgInfo rootOrg = orgApi.getRootOrg();
        if (rootOrg == null || rootOrg.orgIndexCode == null) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, "海康平台的根部门不存在");
        }
        logger.debug("获取根组织：" + rootOrg.orgIndexCode);
        List<String> orgCodeList = orgDao.getOrgCodeList();
        List<Person> toCreateAccounts = new ArrayList<>();
        List<Person> toUpdateAccounts = new ArrayList<>();

        PersonApi personApi = new PersonApi();

        for (ElasticsearchApi.Account account : iamAccountList) {
            if (StringUtils.isEmpty(account.id)) {
                logger.debug("用户人员编码不能为空");
                return CommonUtil.errorJson(Constants.ERROR_400, "用户人员编码不能为空");
            }
            if (StringUtils.isEmpty(account.name)) {
                logger.debug("用户姓名不能为空");
                return CommonUtil.errorJson(Constants.ERROR_400, "用户姓名不能为空");
            }
            if (StringUtils.isEmpty(account.orgId)) {
                logger.debug("用户归属部门不能为空");
                return CommonUtil.errorJson(Constants.ERROR_400, "用户归属部门不能为空");
            }
            if (StringUtils.isEmpty(account.mobile) && StringUtils.isEmpty(account.certificateNum)) {
                logger.debug(account.name + "用户手机号和身份证号同时为空");
                return CommonUtil.errorJson(Constants.ERROR_400, account.name + "用户手机号和身份证号同时为空");
            }
            if (!StringUtils.isEmpty(account.mobile) && (account.mobile.length() < 8 || account.mobile.length() > 20)) {
                logger.debug(account.name + "用户手机号不符合要求:" + account.mobile);
                return CommonUtil.errorJson(Constants.ERROR_400, account.name + "用户手机号不符合要求:" + account.mobile);
            }
            if (!StringUtils.isEmpty(account.certificateNum) && (account.certificateNum.length() < 8 || account.certificateNum.length() > 20)) {
                logger.debug(account.name + "用户手机号不符合要求:" + account.certificateNum);
                return CommonUtil.errorJson(Constants.ERROR_400, account.name + "用户手机号不符合要求:" + account.certificateNum);
            }
            if (!orgCodeList.contains(account.orgId)) {
                logger.debug(account.name + "用户归属部门不存在:" + account.orgId);
                return CommonUtil.errorJson(Constants.ERROR_400, account.name + "用户归属部门不存在:" + account.orgId);
            }
            Person person = personApi.getPersonById(account.id);
            if (person == null) {
                person = new Person(rootOrg.orgIndexCode, account);
                toCreateAccounts.add(person);
            } else {
                person.updateBy(account);
                toUpdateAccounts.add(person);
            }
        }

        if (toCreateAccounts.size() > 0) {
            logger.debug("往海康批量添加人员,数量：" + toCreateAccounts.size());
            BatchAddPersonResponse batchAddPersonResponse = personApi.batchAddPerson(toCreateAccounts);
            if (batchAddPersonResponse.failures != null && batchAddPersonResponse.failures.size() > 0) {
                return CommonUtil.errorJson(Constants.ERROR_GATEWAY, "往海康平台添加人员失败:" + batchAddPersonResponse.failures.get(0).msg);
            }
            personApi.batchBindingCard(toCreateAccounts);
        }
        if (toUpdateAccounts.size() > 0) {
            logger.debug("往海康修改人员,数量：" + toUpdateAccounts.size());
            for (Person person : toUpdateAccounts) {
                personApi.updatePerson(person);
            }
        }
        logger.debug("更新本地数据库,数量：" + iamAccountList.size());
        for (ElasticsearchApi.Account account : iamAccountList) {
            accountDao.insertOrUpdateAccount(account);
        }
        return CommonUtil.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject deleteUsers(List<String> deleteUserIds) {
        logger.debug("deleteUsers");
        if (deleteUserIds == null || deleteUserIds.size() == 0) {
            return CommonUtil.errorJson(Constants.ERROR_400, "请求体为空");
        }
        if (deleteUserIds.size() > 1000) {
            return CommonUtil.errorJson(Constants.ERROR_400, "单次删除数量超过1000");
        }
        List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList = policyDao.getGatewayPolicyListByFromIds(deleteUserIds);
        if (gatewayPolicyList != null && gatewayPolicyList.size() > 0) {
            gatewayPolicyService.addOrDeleteGatewayPolicy(gatewayPolicyList, true);
        }
        accountDao.batchDeleteAccountByIds(deleteUserIds);
        return CommonUtil.successJson();
    }
}
