package com.tellhow.industry.iot.system.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.gateway.hikvision.org.model.OrgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgDao {

    List<JSONObject> listOrg();

    int queryExistOrgName(JSONObject jsonObject);

    int queryExistParentOrgCode(JSONObject jsonObject);

    int queryExistOrgCode(JSONObject jsonObject);

    int addOrg(JSONObject jsonObject);

    int insertOrUpdateOrg(@Param("orgInfo") OrgInfo orgInfo);

    int updateOrg(JSONObject jsonObject);

    int deleteOrg(JSONObject jsonObject);

    int tempDeleteAllOrg();
}
