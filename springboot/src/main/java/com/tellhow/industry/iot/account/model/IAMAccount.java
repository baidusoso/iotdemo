package com.tellhow.industry.iot.account.model;

import lombok.Data;

@Data
public class IAMAccount {
    public String id;
    public String orgId;
    public String name;
    public String delFlag;
    public String email;
    public String gender;

    public String idCard;
    public String loginName;
    public String mobile;

    public String no;

    public String userGroup = "厂内人员";

}
