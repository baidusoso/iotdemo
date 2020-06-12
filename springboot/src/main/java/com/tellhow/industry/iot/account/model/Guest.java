package com.tellhow.industry.iot.account.model;

public class Guest {
    public String id;
    public String userId;
    public String name;
    public String certificateNum;
    public String mobile;
    public String gender;
    public String targetUserId;
    public String targetUserName;
    public String passtimeStartAt;
    public String passtimeEndAt;
    public String facePic;
    public String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getPasstimeStartAt() {
        return passtimeStartAt;
    }

    public void setPasstimeStartAt(String passtimeStartAt) {
        this.passtimeStartAt = passtimeStartAt;
    }

    public String getPasstimeEndAt() {
        return passtimeEndAt;
    }

    public void setPasstimeEndAt(String passtimeEndAt) {
        this.passtimeEndAt = passtimeEndAt;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getFacePic() {
        return facePic;
    }

    public void setFacePic(String facePic) {
        this.facePic = facePic;
    }
}
