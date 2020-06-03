package com.tellhow.industry.iot.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.dao.GatewayDao;
import com.tellhow.industry.iot.gateway.dao.GatewayPolicyDao;
import com.tellhow.industry.iot.gateway.service.GatewayPolicyService;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.gateway.GatewayApi;
import com.tellhow.industry.iot.hikvision.gateway.model.AddAuthDownloadTaskRequest;
import com.tellhow.industry.iot.hikvision.gateway.model.AuthDownloadData;
import com.tellhow.industry.iot.hikvision.gateway.model.AuthDownloadRequest;
import com.tellhow.industry.iot.hikvision.gateway.model.Gateway;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList = ElasticsearchApi.getGatewayPolicyList();
            policyDao.tempDeleteAllGatewayPolicy();
            if (gatewayPolicyList != null && gatewayPolicyList.size() > 0) {
                for (ElasticsearchApi.GatewayPolicy gatewayPolicy : gatewayPolicyList) {
                    policyDao.insertOrUpdateGatewayPolicy(gatewayPolicy);
                }
            }
        } catch (GatewayException gatewayException) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, gatewayException.getMessage());
        }
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject addGatewayPolicy(List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList) {
        //TODO
        if (gatewayPolicyList != null && gatewayPolicyList.size() > 0) {
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
                    if ((account.mobile == null || account.mobile.length() < 8) && (account.certificateNum == null || account.certificateNum.length() < 8)) {
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
            commitTask(gatewayPolicyList, accountIdMap, gatewayIdMap);

        }
        return CommonUtil.successJson();
    }

    void commitTask(final List<ElasticsearchApi.GatewayPolicy> gatewayPolicyList, Map<String, ElasticsearchApi.Account> accountIdMap, Map<String, Gateway.Door> gatewayIdMap) {
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
                    gatewayApi.addAuthDownloadData(authDownloadData);
                    //face
                    logger.info(gatewayPolicy.gatewayId + "-----" + gatewayPolicy.userId + "添加人脸信息");
                    authDownloadData.taskId = faceTaskId;
                    gatewayApi.addAuthDownloadData(authDownloadData);
                }

                AuthDownloadRequest authDownloadRequest = new AuthDownloadRequest(cardTaskId);
                logger.info(cardTaskId + "开始下载任务");
                gatewayApi.startAuthDownloadTask(authDownloadRequest);
                boolean isDownloadFinished = false;
                long start = System.currentTimeMillis();
                do {
                    Thread.sleep(4000);
                    logger.info(cardTaskId + "查询下载任务进度");
                    isDownloadFinished = gatewayApi.queryAuthDownloadTaskProgress(authDownloadRequest);
                    logger.info(cardTaskId + "查询下载任务进度 isDownloadFinished:" + isDownloadFinished);
                } while (!isDownloadFinished || System.currentTimeMillis() - start > 3600 * 1000);

                logger.info(faceTaskId + "开始下载任务");
                authDownloadRequest = new AuthDownloadRequest(faceTaskId);
                gatewayApi.startAuthDownloadTask(authDownloadRequest);
                start = System.currentTimeMillis();
                do {
                    Thread.sleep(4000);
                    logger.info(faceTaskId + "查询下载任务进度");
                    isDownloadFinished = gatewayApi.queryAuthDownloadTaskProgress(authDownloadRequest);
                    logger.info(faceTaskId + "查询下载任务进度 isDownloadFinished:" + isDownloadFinished);
                } while (!isDownloadFinished || System.currentTimeMillis() - start > 3600 * 1000);
                logger.info("commitTask done!!!");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }).start();
    }
}
