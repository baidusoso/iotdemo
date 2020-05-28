package com.tellhow.industry.iot.system.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface OrgDao {

    List<JSONObject> listOrg();

    int queryExistOrgName(JSONObject jsonObject);

    int queryExistParentOrgCode(JSONObject jsonObject);

    int queryExistOrgCode(JSONObject jsonObject);

    int addOrg(JSONObject jsonObject);

    int updateOrg(JSONObject jsonObject);

    int deleteOrg(JSONObject jsonObject);
}
