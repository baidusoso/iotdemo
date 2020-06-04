package com.tellhow.industry.iot.hikvision.gateway.model;

import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.ArrayList;
import java.util.List;

public class AuthItemSearchRequest {
    public List<String> personIds;
    public String queryType = "door";
    public List<ResourceInfo> resourceInfos;
    public int pageNo = 1;
    public int pageSize = 1000;

    public AuthItemSearchRequest(ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        personIds = new ArrayList<>();
        personIds.add(account.id);

        resourceInfos = new ArrayList<>();
        ResourceInfo resourceInfo = new ResourceInfo(doorGateway);
        resourceInfos.add(resourceInfo);
    }
}
