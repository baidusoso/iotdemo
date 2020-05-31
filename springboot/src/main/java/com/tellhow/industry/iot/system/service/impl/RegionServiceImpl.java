package com.tellhow.industry.iot.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.region.RegionApi;
import com.tellhow.industry.iot.hikvision.region.model.RegionInfo;
import com.tellhow.industry.iot.system.dao.RegionDao;
import com.tellhow.industry.iot.system.model.Region;
import com.tellhow.industry.iot.system.service.RegionService;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionServiceImpl implements RegionService {

    private final static Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    @Autowired
    private RegionDao regionDao;

    @Override
    public JSONObject getRootRegion() {
        Region rootRegion = null;
        List<JSONObject> regions = regionDao.listRegion();
        if (regions != null && regions.size() > 0) {
            Map<String, Region> regionMap = new HashMap<>();
            List<Region> regionList = new ArrayList<>();
            for (JSONObject jsonObject : regions) {
                String regionCode = jsonObject.getString("regionCode");
                String regionName = jsonObject.getString("regionName");
                String parentRegionCode = jsonObject.getString("parentRegionCode");
                Region region = new Region();
                region.setRegionCode(regionCode);
                region.setRegionName(regionName);
                region.setLabel(regionName);
                region.setParentRegionCode(parentRegionCode);
                regionList.add(region);
                regionMap.put(regionCode, region);
            }
            for (Region region : regionList) {
                String parentRegionCode = region.getParentRegionCode();
                Region parentRegion = regionMap.get(parentRegionCode);
                if (parentRegion != null) {
                    parentRegion.addChild(region);
                    region.setParentRegion(parentRegion);
                }
            }
            for (Region region : regionList) {
                if (region.getParentRegion() == null) {
                    rootRegion = region;
                    break;
                }
            }

        }
        return CommonUtil.successJson(JSONObject.toJSON(rootRegion));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject syncRegion() {
        try {
            List<RegionInfo> regionList = new RegionApi().getRegionList();
            regionDao.tempDeleteAllRegion();
            if (regionList != null && regionList.size() > 0) {
                for (RegionInfo region : regionList) {
                    regionDao.insertOrUpdateRegion(region);
                }
            }
        } catch (GatewayException gatewayException) {
            return CommonUtil.errorJson(Constants.ERROR_GATEWAY, gatewayException.getMessage());
        }
        return CommonUtil.successJson();
    }
}
