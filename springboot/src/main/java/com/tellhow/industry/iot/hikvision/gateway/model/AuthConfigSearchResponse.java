package com.tellhow.industry.iot.hikvision.gateway.model;

import java.util.List;

public class AuthConfigSearchResponse {
    public int total;
    public int pageSize;
    public int pageNo;
    public List<AuthConfig> list;

    public static class AuthConfig {
        public String personDataId;
        public String personDataType;
        public String resourceIndexCode;
        public String resourceType;
        public String resourceDataType;
        public String channelNo;
        public String startTime;
        public String endTime;
    }
}
