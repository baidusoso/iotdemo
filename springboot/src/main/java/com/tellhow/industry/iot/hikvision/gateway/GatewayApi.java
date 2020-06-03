package com.tellhow.industry.iot.hikvision.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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

import static com.tellhow.industry.iot.hikvision.gateway.model.AddAuthDownloadTaskRequest.TASK_TYPE_CARD;
import static com.tellhow.industry.iot.hikvision.gateway.model.AddAuthDownloadTaskRequest.TASK_TYPE_FACE;

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
}
