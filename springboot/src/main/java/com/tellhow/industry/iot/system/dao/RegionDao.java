package com.tellhow.industry.iot.system.dao;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.hikvision.region.model.RegionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionDao {

    List<JSONObject> listRegion();

    int insertOrUpdateRegion(@Param("regionInfo") RegionInfo region);

    int tempDeleteAllRegion();
}
