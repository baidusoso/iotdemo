package com.tellhow.industry.iot.gateway.hikvision.region;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tellhow.industry.iot.gateway.hikvision.BaseApi;
import com.tellhow.industry.iot.gateway.hikvision.BaseResponse;
import com.tellhow.industry.iot.gateway.hikvision.GatewayException;
import com.tellhow.industry.iot.gateway.hikvision.region.model.GetRegionListRequest;
import com.tellhow.industry.iot.gateway.hikvision.region.model.GetRegionListResponse;
import com.tellhow.industry.iot.gateway.hikvision.region.model.Region;

import java.util.ArrayList;
import java.util.List;

public class RegionApi extends BaseApi {

    public List<Region> getRegionList() {
        List<Region> regionList = new ArrayList<>();
        int pageNo = 1, pageSize = 1000;
        while (true) {
            GetRegionListRequest getRegionListRequest = new GetRegionListRequest(pageNo, pageSize);
            BaseResponse<GetRegionListResponse> response = post(new TypeReference<BaseResponse<GetRegionListResponse>>() {
            }, RegionInterface.PATH_GET_REGION_LIST, JSON.toJSONString(getRegionListRequest));
            if (response.data == null) {
                throw new GatewayException(BaseResponse.ERR_NO_DATA);
            }
            GetRegionListResponse data = response.data;
            int total = data.total;
            if (data.list != null) {
                regionList.addAll(data.list);
            }
            if (pageNo * pageSize >= total) {
                break;
            }
            pageNo++;
        }
        return regionList;
    }
}
