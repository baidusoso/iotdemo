package com.tellhow.industry.iot.hikvision.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.hikvision.BaseApi;
import com.tellhow.industry.iot.hikvision.BaseResponse;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.PageRequest;
import com.tellhow.industry.iot.hikvision.gateway.model.*;
import com.tellhow.industry.iot.system.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GatewayApi extends BaseApi {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public List<Gateway.Door> getGatewayDoorList() {
        List<Gateway.Door> getwayDoorList = new ArrayList<>();
        int pageNo = 1, pageSize = 1000;
        while (true) {
            PageRequest pageRequest = new PageRequest(pageNo, pageSize);
            BaseResponse<GetGatewayDoorListResponse> response = post(new TypeReference<BaseResponse<GetGatewayDoorListResponse>>() {
            }, GatewayInterface.PATH_GET_DOOR_LIST, JSON.toJSONString(pageRequest));
            if (response.data == null) {
                throw new GatewayException(BaseResponse.ERR_NO_DATA);
            }
            GetGatewayDoorListResponse data = response.data;
            int total = data.total;
            logger.debug("total:" + total);
            if (data.list != null) {
                getwayDoorList.addAll(data.list);
            }
            if (pageNo * pageSize >= total) {
                break;
            }
            pageNo++;
        }
        return getwayDoorList;
    }

    public String createAuthDownloadTask(int taskType) {
        AddAuthDownloadTaskRequest addTaskRequest = new AddAuthDownloadTaskRequest(taskType);
        BaseResponse<AddAuthDownloadTaskResponse> response = post(new TypeReference<BaseResponse<AddAuthDownloadTaskResponse>>() {
        }, GatewayInterface.PATH_CREATE_AUTH_DOWNLOAD_TASK, JSON.toJSONString(addTaskRequest));
        return response.data.taskId;
    }

    public void addAuthDownloadData(AuthDownloadData authDownloadData) {
        post(new TypeReference<BaseResponse<Object>>() {
        }, GatewayInterface.PATH_ADD_AUTH_DOWNLOAD_DATA, JSON.toJSONString(authDownloadData));
    }

    public void startAuthDownloadTask(AuthDownloadRequest authDownloadRequest) {
        post(new TypeReference<BaseResponse<Object>>() {
        }, GatewayInterface.PATH_START_AUTH_DOWNLOAD_TASK, JSON.toJSONString(authDownloadRequest));
    }

    public boolean queryAuthDownloadTaskProgress(AuthDownloadRequest authDownloadRequest) {
        BaseResponse<QueryAuthDownloadTaskProgress> response = post(new TypeReference<BaseResponse<QueryAuthDownloadTaskProgress>>() {
        }, GatewayInterface.PATH_QUERY_AUTH_DOWNLOAD_TASK_PROGRESS, JSON.toJSONString(authDownloadRequest));
        logger.info(authDownloadRequest.taskId + " totalPercent:" + response.data.totalPercent + " isDownloadFinished:" + response.data.isDownloadFinished);
        return response.data.isDownloadFinished;
    }

    public AuthConfigSearchResponse searchAuthConfig(ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        AuthConfigSearchRequest authConfigSearchRequest = new AuthConfigSearchRequest(account, doorGateway);
        BaseResponse<AuthConfigSearchResponse> response = post(new TypeReference<BaseResponse<AuthConfigSearchResponse>>() {
        }, GatewayInterface.PATH_AUTH_CONFIG_SEARCH, JSON.toJSONString(authConfigSearchRequest));
        return response.data;
    }

    public AuthConfigSearchResponse searchAuthConfig(Gateway.Door doorGateway, int pageNo, int pageSize) {
        AuthConfigSearchRequest authConfigSearchRequest = new AuthConfigSearchRequest(doorGateway, pageNo, pageSize);
        BaseResponse<AuthConfigSearchResponse> response = post(new TypeReference<BaseResponse<AuthConfigSearchResponse>>() {
        }, GatewayInterface.PATH_AUTH_CONFIG_SEARCH, JSON.toJSONString(authConfigSearchRequest));
        return response.data;
    }

    public AuthItemSearchResponse searchAuthItem(ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        AuthItemSearchRequest authItemSearchRequest = new AuthItemSearchRequest(account, doorGateway);
        BaseResponse<AuthItemSearchResponse> response = post(new TypeReference<BaseResponse<AuthItemSearchResponse>>() {
        }, GatewayInterface.PATH_AUTH_ITEM_LIST_SEARCH, JSON.toJSONString(authItemSearchRequest));
        return response.data;
    }

    public AuthItemSearchResponse.AuthItem searchSingleAuthItem(ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        AuthItemSingleSearchRequest authItemSingleSearchRequest = new AuthItemSingleSearchRequest(account, doorGateway);
        BaseResponse<AuthItemSearchResponse.AuthItem> response = post(new TypeReference<BaseResponse<AuthItemSearchResponse.AuthItem>>() {
        }, GatewayInterface.PATH_AUTH_ITEM_SINGLE_SEARCH, JSON.toJSONString(authItemSingleSearchRequest));
        return response.data;
    }

    public SearchDownloadRecordPersonDetailResponse searchDownloadRecordPersonDetail(String taskId, Gateway.Door doorGateway, ElasticsearchApi.Account account) {
        SearchDownloadRecordPersonDetailRequest searchDownloadRecordPersonDetailRequest = new SearchDownloadRecordPersonDetailRequest(taskId, doorGateway, account);
        BaseResponse<SearchDownloadRecordPersonDetailResponse> response = post(new TypeReference<BaseResponse<SearchDownloadRecordPersonDetailResponse>>() {
        }, GatewayInterface.PATH_SEARCH_DOWNLOAD_RECORD_PERSON_DETAIL, JSON.toJSONString(searchDownloadRecordPersonDetailRequest));
        return response.data;
    }

}
