package com.tellhow.industry.iot.hikvision.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tellhow.industry.iot.hikvision.BaseApi;
import com.tellhow.industry.iot.hikvision.BaseResponse;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.PageRequest;
import com.tellhow.industry.iot.hikvision.gateway.model.Gateway;
import com.tellhow.industry.iot.hikvision.gateway.model.GetGatewayDoorListResponse;
import com.tellhow.industry.iot.hikvision.gateway.model.GetGatewayListResponse;
import com.tellhow.industry.iot.system.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GatewayApi extends BaseApi {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public List<Gateway> getGatewayList() {
        List<Gateway> gatewayList = new ArrayList<>();
        int pageNo = 1, pageSize = 1000;
        while (true) {
            PageRequest pageRequest = new PageRequest(pageNo, pageSize);
            BaseResponse<GetGatewayListResponse> response = post(new TypeReference<BaseResponse<GetGatewayListResponse>>() {
            }, GatewayInterface.PATH_GET_GATEWAY_LIST, JSON.toJSONString(pageRequest));
            if (response.data == null) {
                throw new GatewayException(BaseResponse.ERR_NO_DATA);
            }
            GetGatewayListResponse data = response.data;
            int total = data.total;
            logger.debug("total:" + total);
            if (data.list != null) {
                gatewayList.addAll(data.list);
            }
            if (pageNo * pageSize >= total) {
                break;
            }
            pageNo++;
        }
        return gatewayList;
    }

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
}
