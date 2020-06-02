package com.tellhow.industry.iot.hikvision.person.model;

import java.util.ArrayList;
import java.util.List;

public class AddPersonRequest {
    /**
     * 	唯一标志，不允许与其他人员personId重复，包括已删除的人员；
     * 	值为空或者不传此字段时平台自动生成唯一标志
     */
    public String personId;//非必须
    /**
     * 	人员名称，1~32个字符；不能包含 ’ / \ : * ? " < >
     */
    public String personName;//必须
    /**
     * 	性别
     * 1：男
     * 2：女
     * 0：未知
     */
    public String  gender;//非必须
    /**
     * 	所属组织标识，必须是已存在组织，
     * 	从获取组织列表接口获取返回参数orgIndexCode
     */
    public String orgIndexCode;//必须
    /**
     * 	出生日期，举例：1992-09-12
     */
    public String birthday;//非必须
    /**
     * 	手机号，1-20位数字,平台上人员信息实名标识选择为手机号码时必填
     */
    public String phoneNo;//非必须
    /**
     * 邮箱，举例：hic@163.com
     */
    public String email;//非必须
    /**
     * 	证件类型
     * 111:身份证
     * 414:护照
     * 113:户口簿
     * 335:驾驶证
     * 131:工作证
     * 133:学生证
     * 990:其他
     * 平台上人员信息实名标识选择为身份证件时必填
     */
    public String certificateType;//非必须
    /**
     * 	证件号码，1-20位数字字母，平台上人员信息实名标识选择为身份证件时必填
     */
    public String certificateNo;//非必须
    /**
     * 	工号,平台上人员信息实名标识选择为工号时必填，1-32个字符
     */
    public String jobNo;//非必须
    /**
     * 	人脸信息
     */
    public List<Faces> faces=new ArrayList<>();//非必须


}
