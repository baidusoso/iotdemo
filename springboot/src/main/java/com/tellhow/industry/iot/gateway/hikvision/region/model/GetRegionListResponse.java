package com.tellhow.industry.iot.gateway.hikvision.region.model;

import java.util.List;

public class GetRegionListResponse {
    public int total;
    public int pageNo;
    public int pageSize;

    public List<RegionInfo> list;
}
