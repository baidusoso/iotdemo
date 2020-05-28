package com.tellhow.industry.iot.gateway.hikvision.org.model;

import java.util.List;

public class GetOrgListResponse {
    public int total;
    public int pageNo;
    public int pageSize;

    public List<OrgInfo> list;
}
