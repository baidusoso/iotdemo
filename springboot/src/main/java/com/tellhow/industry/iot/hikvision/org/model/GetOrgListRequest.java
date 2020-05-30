package com.tellhow.industry.iot.hikvision.org.model;

import com.tellhow.industry.iot.hikvision.PageRequest;

public class GetOrgListRequest extends PageRequest {
    /**
     * 当前页码
     * 当前页码需大于0（pageNo>0）
     */
    public int pageNo;
    /**
     * 每页记录总数
     * 每页记录展示的数目应大于0，小于等于1000（0<pageSize<=1000）
     */
    public int pageSize;

    public GetOrgListRequest(int pageNo, int pageSize) {
        super(pageNo, pageSize);
    }

}
