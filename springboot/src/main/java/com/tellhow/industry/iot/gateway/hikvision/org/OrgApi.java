package com.tellhow.industry.iot.gateway.hikvision.org;

import com.alibaba.fastjson.JSON;
import com.tellhow.industry.iot.gateway.hikvision.BaseApi;
import com.tellhow.industry.iot.gateway.hikvision.BaseResponse;
import com.tellhow.industry.iot.gateway.hikvision.GatewayException;
import com.tellhow.industry.iot.gateway.hikvision.org.model.GetOrgListRequest;
import com.tellhow.industry.iot.gateway.hikvision.org.model.GetOrgListResponse;
import com.tellhow.industry.iot.gateway.hikvision.org.model.OrgInfo;

import java.util.ArrayList;
import java.util.List;

public class OrgApi extends BaseApi {

    public OrgInfo getRootOrg() {
        BaseResponse<OrgInfo> getRootOrgResponse = post(OrgInterface.PATH_GET_ROOT, null);
        if(getRootOrgResponse.data!=null){
        }
        return getRootOrgResponse.data;
    }

    public List<OrgInfo> getOrgList() {
        List<OrgInfo> orgInfoList = new ArrayList<>();
        int pageNo = 1, pageSize = 1000;
        while (true) {
            GetOrgListRequest getOrgListRequest = new GetOrgListRequest(pageNo, pageSize);
            BaseResponse<GetOrgListResponse> response = post(OrgInterface.PATH_GET_ORG_LIST, JSON.toJSONString(getOrgListRequest));
            if (response.data == null) {
                throw new GatewayException("海康安防管理平台返回空数据");
            }
            GetOrgListResponse data = response.data;
            int total = data.total;
            if (data.list != null) {
                orgInfoList.addAll(data.list);
            }
            if (pageNo * pageSize >= total) {
                break;
            }
            pageNo++;
        }
        return orgInfoList;
    }
}
