package com.tellhow.industry.iot.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.account.service.AccountService;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.dao.GatewayDao;
import com.tellhow.industry.iot.gateway.dao.GatewayPolicyDao;
import com.tellhow.industry.iot.gateway.model.DeleteGatewayPolicyRequest;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.gateway.GatewayApi;
import com.tellhow.industry.iot.hikvision.gateway.model.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        for (IAMAccount iamAccount : iamAccountList) {
            if (StringUtils.isEmpty(iamAccount.id)) {
                logger.debug("用户人员编码不能为空");
                return CommonUtil.errorJson(Constants.ERROR_400, "用户人员编码不能为空");
            }
            if (StringUtils.isEmpty(iamAccount.name)) {
                logger.debug("用户姓名不能为空");
                return CommonUtil.errorJson(Constants.ERROR_400, "用户姓名不能为空");
            }
            if (StringUtils.isEmpty(iamAccount.orgId)) {
                logger.debug("用户归属部门不能为空");
                return CommonUtil.errorJson(Constants.ERROR_400, "用户归属部门不能为空");
            }
            if (StringUtils.isEmpty(iamAccount.mobile) && StringUtils.isEmpty(iamAccount.idCard)) {
                logger.debug(iamAccount.name + "用户手机号和身份证号同时为空");
                return CommonUtil.errorJson(Constants.ERROR_400, iamAccount.name + "用户手机号和身份证号同时为空");
            }
            if (!StringUtils.isEmpty(iamAccount.mobile) && (iamAccount.mobile.length() < 8 || iamAccount.mobile.length() > 20)) {
                logger.debug(iamAccount.name + "用户手机号不符合要求:" + iamAccount.mobile);
                return CommonUtil.errorJson(Constants.ERROR_400, iamAccount.name + "用户手机号不符合要求:" + iamAccount.mobile);
            }
            if (!StringUtils.isEmpty(iamAccount.idCard) && (iamAccount.idCard.length() < 8 || iamAccount.idCard.length() > 20)) {
                logger.debug(iamAccount.name + "用户手机号不符合要求:" + iamAccount.idCard);
                return CommonUtil.errorJson(Constants.ERROR_400, iamAccount.name + "用户手机号不符合要求:" + iamAccount.idCard);
            }
            if (!orgCodeList.contains(iamAccount.orgId)) {
                logger.debug(iamAccount.name + "用户归属部门不存在:" + iamAccount.orgId);
                return CommonUtil.errorJson(Constants.ERROR_400, iamAccount.name + "用户归属部门不存在:" + iamAccount.orgId);
            }
            Person person = personApi.getPersonById(iamAccount.id);
            if (person == null) {
                person = new Person(rootOrg.orgIndexCode, iamAccount);
                toCreateAccounts.add(person);
            } else {
                person.updateBy(iamAccount);
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
        for (IAMAccount iamAccount : iamAccountList) {
            accountDao.insertOrUpdateAccount(new ElasticsearchApi.Account(iamAccount));
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
            Map<String, ElasticsearchApi.Account> accountIdMap = new HashMap<>();
            Map<String, Gateway.Door> gatewayIdMap = new HashMap<>();
            for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                //2、校验用户是否存在
                ElasticsearchApi.Account account = accountIdMap.get(gatewayPolicy.userId);
                if (account == null) {
                    account = accountDao.getAccountById(gatewayPolicy.userId);
                    if (account == null) {
                        return CommonUtil.errorJson(Constants.ERROR_400, gatewayPolicy.userId + "用户不存在");
                    }
                    if (account.isGuest() && (account.mobile == null || account.mobile.length() < 8) && (account.certificateNum == null || account.certificateNum.length() < 8)) {
                        return CommonUtil.errorJson(Constants.ERROR_400, account.name + "用户的手机号或身份证号不满足创建卡号要求");
                    }
                    if (account.isGuest() && StringUtils.isEmpty(account.facePic)) {
                        return CommonUtil.errorJson(Constants.ERROR_400, account.name + "的人脸尚未上传");
                    }
                    accountIdMap.put(gatewayPolicy.userId, account);
                }
                //3、校验门禁是否存在
                Gateway.Door doorGateway = gatewayIdMap.get(gatewayPolicy.gatewayId);
                if (doorGateway == null) {
                    doorGateway = gatewayDao.getGatewayDoorById(gatewayPolicy.gatewayId);
                    if (doorGateway == null) {
                        return CommonUtil.errorJson(Constants.ERROR_400, gatewayPolicy.gatewayId + "门禁不存在");
                    }
                    gatewayIdMap.put(gatewayPolicy.gatewayId, doorGateway);
                }
            }
            //创建card任务
            commitTask(gatewayPolicyList, accountIdMap, gatewayIdMap, true);
        }
        accountDao.batchDeleteAccountByIds(deleteUserIds);
        return CommonUtil.successJson();
    }

    void commitTask(final List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList, Map<String, ElasticsearchApi.Account> accountIdMap, Map<String, Gateway.Door> gatewayIdMap, boolean delete) {
        new Thread(() -> {
            try {
                PersonApi personApi = new PersonApi();
                OrgApi orgApi = new OrgApi();
                OrgInfo rootOrg = null;
                String defaultFaceGroup = null;
                Map<String, Person> personMap = new HashMap<>();
                for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                    ElasticsearchApi.Account account = accountIdMap.get(gatewayPolicy.userId);
                    if (account.isGuest()) {
                        Person person = personMap.get(account.id);
                        if (person == null) {
                            person = personApi.getPersonById(account.id);
                            if (person == null) {
                                if (rootOrg == null) {
                                    rootOrg = orgApi.getRootOrg();
                                }
                                if (rootOrg == null) {
                                    logger.info("rootOrg is null");
                                    return;
                                }
                                account.orgId = rootOrg.orgIndexCode;
                                String personId = personApi.addPerson(account);
                                logger.info("addPerson result:" + personId);
                                account.faceId = null;
                            }
                            personMap.put(account.id, person);
                        }
                        if (account.faceId == null) {
                            if (defaultFaceGroup == null) {
                                defaultFaceGroup = personApi.queryDefaultFaceGroup();
                                if (defaultFaceGroup == null) {
                                    defaultFaceGroup = personApi.addDefaultFaceGroup();
                                }
                                if (defaultFaceGroup == null) {
                                    logger.error("Fail to add default face group!");
                                    return;
                                }
                            }
                            account.faceId = personApi.addFace(defaultFaceGroup, account);
                            logger.info("addFace result:" + account.faceId);
                            accountDao.updateFaceId(account.id, account.faceId);
                        }
                    }
                }
                GatewayApi gatewayApi = new GatewayApi();
                String cardTaskId = gatewayApi.createAuthDownloadTask(AddAuthDownloadTaskRequest.TASK_TYPE_CARD);
                logger.info("cardTaskId:" + cardTaskId);
                String faceTaskId = gatewayApi.createAuthDownloadTask(AddAuthDownloadTaskRequest.TASK_TYPE_FACE);
                logger.info("faceTaskId:" + faceTaskId);
                for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                    logger.info(gatewayPolicy.gatewayId + "-----" + gatewayPolicy.userId + "添加卡片信息");
                    //card
                    AuthDownloadData authDownloadData = new AuthDownloadData(cardTaskId, gatewayPolicy, accountIdMap.get(gatewayPolicy.userId), gatewayIdMap.get(gatewayPolicy.gatewayId));
                    authDownloadData.setOperationType(delete);
                    gatewayApi.addAuthDownloadData(authDownloadData);
                    //face
                    logger.info(gatewayPolicy.gatewayId + "-----" + gatewayPolicy.userId + "添加人脸信息");
                    authDownloadData.taskId = faceTaskId;
                    authDownloadData.setOperationType(delete);
                    gatewayApi.addAuthDownloadData(authDownloadData);
                }
                boolean isCardTaskDownloadFinished = queryAuthDownloadTaskProgress(gatewayApi, cardTaskId);
                logger.info("isCardTaskDownloadFinished:" + isCardTaskDownloadFinished);
                if (isCardTaskDownloadFinished) {
                    boolean isFaceTaskDownloadFinished = queryAuthDownloadTaskProgress(gatewayApi, faceTaskId);
                    logger.info("isFaceTaskDownloadFinished:" + isFaceTaskDownloadFinished);
                    if (isFaceTaskDownloadFinished) {
                        for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                            if (!delete) {
                                syncAddAuthConfig(gatewayApi, gatewayPolicy, accountIdMap, gatewayIdMap);
                            } else {
                                syncDeleteAuthConfig(gatewayApi, gatewayPolicy, accountIdMap, gatewayIdMap);
                            }
                        }
                    }
                }
                logger.info("commitTask done!!!");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }).start();
    }

    boolean queryAuthDownloadTaskProgress(GatewayApi gatewayApi, String taskId) {
        AuthDownloadRequest authDownloadRequest = new AuthDownloadRequest(taskId);
        logger.info(taskId + "开始下载任务");
        gatewayApi.startAuthDownloadTask(authDownloadRequest);
        boolean isDownloadFinished = false;
        long start = System.currentTimeMillis();
        do {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(taskId + "查询下载任务进度");
            isDownloadFinished = gatewayApi.queryAuthDownloadTaskProgress(authDownloadRequest);
            logger.info(taskId + "查询下载任务进度 isDownloadFinished:" + isDownloadFinished);
        } while (!isDownloadFinished || System.currentTimeMillis() - start > 3600 * 1000);
        return isDownloadFinished;
    }

    void syncAddAuthConfig(GatewayApi gatewayApi, ElasticsearchApi.GatewayPolicy gatewayPolicy, Map<String, ElasticsearchApi.Account> accountIdMap, Map<String, Gateway.Door> gatewayIdMap) {
        AuthConfigSearchResponse authConfigSearchResponse = gatewayApi.searchAuthConfig(accountIdMap.get(gatewayPolicy.userId), gatewayIdMap.get(gatewayPolicy.gatewayId));
        if (authConfigSearchResponse != null && authConfigSearchResponse.list != null && authConfigSearchResponse.list.size() > 0) {
            logger.info("syncAddAuthConfig list size:" + authConfigSearchResponse.list.size());
            AuthConfigSearchResponse.AuthConfig authConfig = authConfigSearchResponse.list.get(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdfISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            if (gatewayPolicy.id == null) {
                gatewayPolicy.id = UUID.randomUUID().toString();
            }
            try {
                gatewayPolicy.startAt = sdf.format(sdfISO8601.parse(authConfig.startTime));
                gatewayPolicy.endAt = sdf.format(sdfISO8601.parse(authConfig.endTime));
                policyDao.insertOrUpdateGatewayPolicy(gatewayPolicy);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    void syncDeleteAuthConfig(GatewayApi gatewayApi, ElasticsearchApi.GatewayPolicy gatewayPolicy, Map<String, ElasticsearchApi.Account> accountIdMap, Map<String, Gateway.Door> gatewayIdMap) {
        AuthConfigSearchResponse authConfigSearchResponse = gatewayApi.searchAuthConfig(accountIdMap.get(gatewayPolicy.userId), gatewayIdMap.get(gatewayPolicy.gatewayId));
        if (authConfigSearchResponse != null && (authConfigSearchResponse.list == null || authConfigSearchResponse.list.size() == 0)) {
            logger.info("syncDeleteAuthConfig list size is empty!");
            policyDao.deleteGatewayPolicy(gatewayPolicy);
        }
    }
}
