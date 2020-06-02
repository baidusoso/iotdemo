package com.tellhow.industry.iot.hikvision.org;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tellhow.industry.iot.hikvision.BaseApi;
import com.tellhow.industry.iot.hikvision.BaseResponse;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.org.model.GetOrgListRequest;
import com.tellhow.industry.iot.hikvision.org.model.GetOrgListResponse;
import com.tellhow.industry.iot.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.system.service.impl.UserServiceImpl;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrgApi extends BaseApi {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public OrgInfo getRootOrg() {
        BaseResponse<OrgInfo> getRootOrgResponse = post(new TypeReference<BaseResponse<OrgInfo>>() {
        }, OrgInterface.PATH_GET_ROOT, null);
        return getRootOrgResponse.data;
    }

    public List<OrgInfo> getOrgList() {
        List<OrgInfo> orgInfoList = new ArrayList<>();
        int pageNo = 1, pageSize = 1000;
        while (true) {
            GetOrgListRequest getOrgListRequest = new GetOrgListRequest(pageNo, pageSize);
            BaseResponse<GetOrgListResponse> response = post(new TypeReference<BaseResponse<GetOrgListResponse>>() {
            }, OrgInterface.PATH_GET_ORG_LIST, JSON.toJSONString(getOrgListRequest));
            if (response.data == null) {
                throw new GatewayException(BaseResponse.ERR_NO_DATA);
            }
            GetOrgListResponse data = response.data;
            int total = data.total;
            logger.debug("total:" + total);
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

    public static void main(String[] args) {
        new OrgApi().getOrgList();
    }
}
