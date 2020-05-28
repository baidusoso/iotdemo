package com.tellhow.industry.iot.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.system.dao.OrgDao;
import com.tellhow.industry.iot.system.model.Org;
import com.tellhow.industry.iot.system.service.OrgService;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        List<JSONObject> OrgList = orgDao.listOrg();
        if (OrgList != null && OrgList.size() > 0) {
            Map<String, Org> orgMap = new HashMap<>();
            for (JSONObject jsonObject : OrgList) {
                String orgCode = jsonObject.getString("orgCode");
                String orgName = jsonObject.getString("orgName");
                Org org = new Org();
                org.setOrgCode(orgCode);
                org.setOrgName(orgName);
                orgMap.put(orgCode, org);
                if (rootOrg == null) {
                    rootOrg = org;
                    continue;
                }
                String parentOrgCode = jsonObject.getString("parentOrgCode");
                Org parentOrg = orgMap.get(parentOrgCode);
                if (parentOrg != null) {
                    logger.debug(parentOrg.getOrgCode() + " addChild:" + org.getOrgCode());
                    parentOrg.addChild(org);
                } else {
                    logger.debug("parentOrgCode:" + parentOrgCode);
                }
            }

        }
        return CommonUtil.successJson(JSONObject.toJSON(rootOrg));
    }

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

    @Override
    public JSONObject deleteOrg(JSONObject jsonObject) {
        orgDao.deleteOrg(jsonObject);
        return CommonUtil.successJson();
    }
}
