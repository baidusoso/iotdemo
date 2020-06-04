package com.tellhow.industry.iot.hikvision.gateway.model;

import java.util.List;

public class AuthItemSearchResponse {
    public int total;
    public List<AuthItem> list;

    public static class AuthItem {
        public String personId;
        public String resourceIndexCode;
        public String channelNo;
        public int personStatus;
        public int cardStatus;
        public int faceStatus;
        public String configTime;
        public String startTime;
        public String endTime;
    }
}
