package com.tellhow.industry.iot.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.system.dao.OrgDao;
import com.tellhow.industry.iot.system.model.Org;
import com.tellhow.industry.iot.system.service.OrgService;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import com.tellhow.industry.iot.util.constants.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrgServiceImpl implements OrgService {

    private final static Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);

    @Autowired
    private OrgDao orgDao;

    @Override
    public JSONObject getRootOrg() {
        Org rootOrg = null;
        List<JSONObject> orgs = orgDao.listOrg();
        if (orgs != null && orgs.size() > 0) {
            Map<String, Org> orgMap = new HashMap<>();
            List<Org> orgList = new ArrayList<>();
            for (JSONObject jsonObject : orgs) {
                String orgCode = jsonObject.getString("orgCode");
                String orgName = jsonObject.getString("orgName");
                String parentOrgCode = jsonObject.getString("parentOrgCode");
                Org org = new Org();
                org.setOrgCode(orgCode);
                org.setOrgName(orgName);
                org.setLabel(orgName);
                org.setParentOrgCode(parentOrgCode);
                orgList.add(org);
                orgMap.put(orgCode, org);
            }
            for (Org org : orgList) {
                String parentOrgCode = org.getParentOrgCode();
                Org parentOrg = orgMap.get(parentOrgCode);
                if (parentOrg != null) {
                    parentOrg.addChild(org);
                    org.setParentOrg(parentOrg);
                }
            }
            for (Org org : orgList) {
                if (org.getParentOrg() == null) {
                    rootOrg = org;
                    break;
                }
            }

        }
        return CommonUtil.successJson(JSONObject.toJSON(rootOrg));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject addOrg(JSONObject jsonObject) {
        String parentOrgCode = jsonObject.getString("parentOrgCode");
        int exist = 0;
        if (!StringUtils.isEmpty(parentOrgCode)) {
            exist = orgDao.queryExistParentOrgCode(jsonObject);
            if (exist == 0) {
                return CommonUtil.errorJson(ErrorEnum.E_30001);
            }
        }
        exist = orgDao.queryExistOrgName(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_30002);
        }
        orgDao.addOrg(jsonObject);
        return CommonUtil.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject updateOrg(JSONObject jsonObject) {
        int exist = orgDao.queryExistOrgCode(jsonObject);
        if (exist == 0) {
            return CommonUtil.errorJson(ErrorEnum.E_30003);
        }
        exist = orgDao.queryExistOrgName(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_30002);
        }
        orgDao.updateOrg(jsonObject);
        return CommonUtil.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject deleteOrg(JSONObject jsonObject) {
        orgDao.deleteOrg(jsonObject);
        return CommonUtil.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject syncOrg() {
        try {
            List<OrgInfo> orgInfoList = ElasticsearchApi.getOrgList();
            orgDao.tempDeleteAllOrg();
            if (orgInfoList != null && orgInfoList.size() > 0) {
                for (OrgInfo orgInfo : orgInfoList) {
                    orgDao.insertOrUpdateOrg(orgInfo);
                }
            }
        } catch (GatewayException gatewayException) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, gatewayException.getMessage());
        }
        return CommonUtil.successJson();
    }
}
