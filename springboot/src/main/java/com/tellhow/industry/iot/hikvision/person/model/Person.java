package com.tellhow.industry.iot.hikvision.person.model;

import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;

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

    public Person(String orgIndexCode, ElasticsearchApi.Account account) {
        this.orgIndexCode = orgIndexCode;
        this.personId = account.id;
        this.personName = account.name;
        this.phoneNo = account.mobile;
        this.email = account.email;
        if (account.certificateNum != null) {
            this.certificateType = "111";
            this.certificateNo = account.certificateNum;
        }
    }

    public void updateBy(ElasticsearchApi.Account account) {
        this.personName = account.name;
        this.phoneNo = account.mobile;
        this.email = account.email;
        if (account.certificateNum != null) {
            this.certificateType = "111";
            this.certificateNo = account.certificateNum;
        }
    }
}
