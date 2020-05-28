package com.tellhow.industry.iot.system.service;

import com.alibaba.fastjson.JSONObject;

public interface OrgService {

    JSONObject getRootOrg();

    JSONObject addOrg(JSONObject jsonObject);

    JSONObject updateOrg(JSONObject jsonObject);

    JSONObject deleteOrg(JSONObject jsonObject);

    JSONObject syncOrg();
}
