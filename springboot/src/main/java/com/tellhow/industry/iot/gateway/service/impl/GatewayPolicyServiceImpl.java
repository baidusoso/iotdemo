package com.tellhow.industry.iot.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.dao.GatewayDao;
import com.tellhow.industry.iot.gateway.dao.GatewayPolicyDao;
import com.tellhow.industry.iot.gateway.model.AddGatewayPolicyRequest;
import com.tellhow.industry.iot.gateway.model.DeleteGatewayPolicyRequest;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.gateway.GatewayApi;
import com.tellhow.industry.iot.hikvision.gateway.model.*;
import com.tellhow.industry.iot.hikvision.org.OrgApi;
import com.tellhow.industry.iot.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.hikvision.person.PersonApi;
import com.tellhow.industry.iot.hikvision.person.model.Person;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GatewayPolicyServiceImpl implements GatewayPolicyService {

    private Logger logger = LoggerFactory.getLogger(GatewayPolicyServiceImpl.class);

    @Autowired
    GatewayPolicyDao policyDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    GatewayDao gatewayDao;

    @Override
    public JSONObject getGatewayPolicyList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = policyDao.getGatewayPolicyCount(jsonObject);
        List<JSONObject> gatewayPolicyList = policyDao.getGatewayPolicyList(jsonObject);
        return CommonUtil.successPage(jsonObject, gatewayPolicyList, count);
    }

    @Override
    public JSONObject syncGatewayPolicy() {
        commitSyncGatewayPolicyTask();
        return CommonUtil.successJson();
    }

    void commitSyncGatewayPolicyTask() {
        new Thread(() -> {
            List<Gateway.Door> doorList = gatewayDao.getAllGatewayDoors();
            if (doorList != null && doorList.size() > 0) {
                GatewayApi gatewayApi = new GatewayApi();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                int pageSize = 1000;
                int total = 0;
                int pageNo = 1;
                for (Gateway.Door doorGateway : doorList) {
                    logger.info("commitSyncGatewayPolicyTask for doorIndexCode:" + doorGateway.doorIndexCode);
                    do {
                        AuthConfigSearchResponse authConfigSearchResponse = gatewayApi.searchAuthConfig(doorGateway, pageNo, pageSize);
                        if (authConfigSearchResponse != null && authConfigSearchResponse.list != null && authConfigSearchResponse.list.size() > 0) {
                            logger.info("commitSyncGatewayPolicyTask searchAuthConfig size:" + authConfigSearchResponse.list.size());
                            total = authConfigSearchResponse.total;
                            pageNo += 1;
                            for (AuthConfigSearchResponse.AuthConfig authConfig : authConfigSearchResponse.list) {
                                ElasticsearchApi.GatewayPolicy gatewayPolicy = new ElasticsearchApi.GatewayPolicy();
                                gatewayPolicy.id = UUID.randomUUID().toString();
                                gatewayPolicy.gatewayId = authConfig.resourceIndexCode;
                                gatewayPolicy.userId = authConfig.personDataId;
                                try {
                                    gatewayPolicy.startAt = sdf.format(sdfISO8601.parse(authConfig.startTime));
                                    gatewayPolicy.endAt = sdf.format(sdfISO8601.parse(authConfig.endTime));
                                    policyDao.insertOrUpdateGatewayPolicy(gatewayPolicy);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } while (total > pageNo * pageSize);
                }
            }
        }).start();
    }

    @Override
    public JSONObject addGatewayPolicy(AddGatewayPolicyRequest addGatewayPolicyRequest) {
        if (addGatewayPolicyRequest != null && addGatewayPolicyRequest.policies != null) {
            List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList = addGatewayPolicyRequest.policies;
            logger.info("policies count:" + gatewayPolicyList.size());
            if (gatewayPolicyList.size() > 0) {
                if (gatewayPolicyList.size() > 100) {
                    return CommonUtil.errorJson(Constants.ERROR_400, "单次数量超出100");
                }
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                Map<String, ElasticsearchApi.Account> accountIdMap = new HashMap<>();
                Map<String, Gateway.Door> gatewayIdMap = new HashMap<>();
                for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                    //1、日期校验与格式为海康要求的ISO8601
                    if (StringUtils.isEmpty(gatewayPolicy.startAt) || StringUtils.isEmpty(gatewayPolicy.endAt)) {
                        return CommonUtil.errorJson(Constants.ERROR_400, "时间格式错误");
                    }
                    try {
                        Date startTime = sdf.parse(gatewayPolicy.startAt);
                        Date endTime = sdf.parse(gatewayPolicy.endAt);
                        if (endTime.before(startTime)) {
                            return CommonUtil.errorJson(Constants.ERROR_400, "结束时间小于开始时间");
                        }
                        if (endTime.before(now)) {
                            return CommonUtil.errorJson(Constants.ERROR_400, "结束时间不能小于当前时间");
                        }
                        gatewayPolicy.startAt = sdfISO8601.format(startTime);
                        gatewayPolicy.endAt = sdfISO8601.format(endTime);
                    } catch (ParseException e) {
                        return CommonUtil.errorJson(Constants.ERROR_400, "时间格式错误");
                    }
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
                commitTask(gatewayPolicyList, accountIdMap, gatewayIdMap, false);
            }
        }
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deleteGatewayPolicy(DeleteGatewayPolicyRequest deleteGatewayPolicyRequest) {
        if (deleteGatewayPolicyRequest != null && deleteGatewayPolicyRequest.ids != null && deleteGatewayPolicyRequest.ids.size() > 0) {
            if (deleteGatewayPolicyRequest.ids.size() > 100) {
                return CommonUtil.errorJson(Constants.ERROR_400, "单次数量超出100");
            }
            List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList = policyDao.getGatewayPolicyListByFromIds(deleteGatewayPolicyRequest.ids);
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
        }
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
