package com.tellhow.industry.iot.gateway.model;

import lombok.Data;

@Data
public class AuditLog {
    private String happenTime;
    private String userID;
    private String srcName;
    private String name;
    private String userGroup;
    private String gender;
    private String no;
    private String result = "禁止通过";
    private String mobiles;
    private String certificateNum;
    private Number ExtEventInOut;
}
