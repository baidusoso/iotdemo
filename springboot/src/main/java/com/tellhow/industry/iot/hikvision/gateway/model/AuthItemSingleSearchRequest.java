package com.tellhow.industry.iot.hikvision.gateway.model;

import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.ArrayList;
import java.util.List;

public class AuthItemSingleSearchRequest {
    public String personId;
    public ResourceInfo resourceInfo;

    public AuthItemSingleSearchRequest(ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        personId = account.id;
        resourceInfo = new ResourceInfo(doorGateway);
    }
}
