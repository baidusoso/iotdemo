package com.tellhow.industry.iot.hikvision.gateway.model;

import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.ArrayList;
import java.util.List;

public class AuthConfigSearchRequest {
    public List<String> personDataIds;
    public String personDataType = "person";
    public List<ResourceInfo> resourceInfos;
    public int pageNo = 1;
    public int pageSize = 1000;

    public AuthConfigSearchRequest(Gateway.Door doorGateway, int pageNo, int pageSize) {
        resourceInfos = new ArrayList<>();
        ResourceInfo resourceInfo = new ResourceInfo(doorGateway);
        resourceInfos.add(resourceInfo);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public AuthConfigSearchRequest(ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        personDataIds = new ArrayList<>();
        personDataIds.add(account.id);

        resourceInfos = new ArrayList<>();
        ResourceInfo resourceInfo = new ResourceInfo(doorGateway);
        resourceInfos.add(resourceInfo);
    }
}
