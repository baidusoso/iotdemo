package com.tellhow.industry.iot.hikvision.gateway.model;

import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

import java.util.ArrayList;
import java.util.List;

public class SearchDownloadRecordPersonDetailRequest {
    public String taskId;
    public ResourceInfo resourceInfo;
    public List<String> personIds;
    public int pageNo = 1;
    public int pageSize = 1000;

    public SearchDownloadRecordPersonDetailRequest(String taskId, Gateway.Door gateway, ElasticsearchApi.Account account) {
        this.taskId = taskId;
        this.resourceInfo = new ResourceInfo(gateway);
        personIds = new ArrayList<>();
        personIds.add(account.id);
    }
}
