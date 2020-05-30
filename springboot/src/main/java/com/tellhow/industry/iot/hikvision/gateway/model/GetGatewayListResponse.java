package com.tellhow.industry.iot.hikvision.gateway.model;

import java.util.List;

public class GetGatewayListResponse {
    public int total;
    public int pageNo;
    public int pageSize;

    public List<Gateway> list;
}
