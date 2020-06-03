package com.tellhow.industry.iot.hikvision.gateway.model;

import java.util.ArrayList;
import java.util.List;

public class ResourceInfo {
    public String resourceIndexCode;
    public String resourceType;
    public List<Integer> channelNos = new ArrayList<>();

    public ResourceInfo(String resourceIndexCode, String resourceType, int channelNo) {
        this.resourceIndexCode = resourceIndexCode;
        this.resourceType = resourceType;
        this.channelNos.add(channelNo);
    }

    public ResourceInfo(Gateway.Door doorGateway) {
        this.resourceIndexCode = doorGateway.doorIndexCode;
        this.resourceType = doorGateway.channelType;
        this.channelNos.add(Integer.parseInt(doorGateway.channelNo));
    }
}
