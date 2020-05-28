package com.tellhow.industry.iot.system.service;

import com.alibaba.fastjson.JSONObject;

public interface RegionService {

    JSONObject getRootRegion();

    JSONObject syncRegion();
}
