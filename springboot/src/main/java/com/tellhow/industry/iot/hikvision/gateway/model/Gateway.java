package com.tellhow.industry.iot.hikvision.gateway.model;

public class Gateway {
    public String acsDevIndexCode;
    public String acsDevName;
    public String acsDevTypeDesc;
    public String acsDevTypeCode;
    public String acsDevTypeName;
    public String acsDevIp;
    public String acsDevPort;
    public String acsDevCode;
    public String regionIndexCode;
    public String treatyType;
    public String createTime;
    public String updateTime;

    public String firm;

    public static class Door {
        public String doorIndexCode;
        public String doorName;
        public int doorNo;
        public String acsDevIndexCode;
        public String regionIndexCode;
        public String channelType;
        public int channelNo;
        public String installLocation;
        public String createTime;
        public String updateTime;
    }
}
