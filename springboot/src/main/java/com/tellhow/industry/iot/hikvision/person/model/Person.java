package com.tellhow.industry.iot.hikvision.person.model;

import com.tellhow.industry.iot.account.model.IAMAccount;

public class Person {
    public String personId;
    public String personName;
    public String orgIndexCode;
    public int gender;
    public String birthday;
    public String phoneNo;
    public String email;
    public String certificateType;
    public String certificateNo;
    public String jobNo;

    public Person() {

    }

    public Person(String personId) {
        this.personId = personId;
    }

    public Person(String orgIndexCode, IAMAccount iamAccount) {
        this.orgIndexCode = orgIndexCode;
        this.personId = iamAccount.id;
        this.personName = iamAccount.name;
        this.phoneNo = iamAccount.mobile;
        this.email = iamAccount.email;
        if (iamAccount.idCard != null) {
            this.certificateType = "111";
            this.certificateNo = iamAccount.idCard;
        }
    }

    public void updateBy(IAMAccount iamAccount) {
        this.personName = iamAccount.name;
        this.phoneNo = iamAccount.mobile;
        this.email = iamAccount.email;
        if (iamAccount.idCard != null) {
            this.certificateType = "111";
            this.certificateNo = iamAccount.idCard;
        }
    }
}
